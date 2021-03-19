package org.nhsrc.web;

import com.bugsnag.Bugsnag;
import com.bugsnag.Report;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.tomcat.util.ExceptionUtils;
import org.nhsrc.web.contract.ext.APIError;
import org.nhsrc.web.framework.GunakBugsnagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

@RestControllerAdvice
public class CustomRestExceptionHandler {
    private final Logger logger;
    private final Bugsnag bugsnag;

    @Autowired
    public CustomRestExceptionHandler(Bugsnag bugsnag) {
        this.logger = LoggerFactory.getLogger(this.getClass());
        this.bugsnag = bugsnag;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity handleException(Exception e, HttpServletRequest req, HttpServletResponse res) {
        if (e instanceof GunakAPIException) {
            GunakAPIException gunakAPIException = (GunakAPIException) e;
            APIError apiError = createAPIError(gunakAPIException.getHttpStatusCode(), gunakAPIException.getMessage());
            logger.info(apiError.toString());
            return new ResponseEntity<>(apiError, gunakAPIException.getHttpStatusCode());
        } else if (e instanceof AccessDeniedException) {
            return handleException(e, HttpStatus.UNAUTHORIZED, "You are not authorised to access the API. Ensure that you have logged in. Else please contact support if you need help.", false, req);
        } else if (e instanceof InvalidDataAccessApiUsageException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "One or more request parameter is missing", true, req);
        } else if (e instanceof ConversionFailedException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "Data type conversion failed. Possible reasons could be - date format being incorrect.", true, req);
        } else if (e instanceof NoHandlerFoundException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "No such URL and HTTP method combination exists.", false, req);
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "Invalid method parameter values.", true, req);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "No such URL and HTTP method combination exists.", false, req);
        } else if (e instanceof HttpMessageNotReadableException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "Data type conversion failed. Possible reasons could be - date format being incorrect.", true, req);
        } else if (e instanceof ClientAbortException) {
            logger.error(e.getMessage(), e);
            return null;
        } else {
            return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error.", true, req);
        }
    }

    public ResponseEntity handleException(Exception e, HttpStatus httpStatus, String message, boolean notifyBugsnag, HttpServletRequest request) {
        if (notifyBugsnag) {
            Report report = bugsnag.buildReport(GunakBugsnagException.create(request, e));
            bugsnag.notify(report);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
        }

        APIError apiError = createAPIError(httpStatus, message);
        logger.error(e.getMessage(), e);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    public APIError createAPIError(HttpStatus httpStatusCode, String message) {
        APIError apiError = new APIError();
        apiError.setHttpStatusCode(httpStatusCode.value());
        apiError.setErrorMessage(message);
        return apiError;
    }
}