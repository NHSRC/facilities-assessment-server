package org.nhsrc.web;

import com.bugsnag.Bugsnag;
import com.bugsnag.Report;
import org.apache.tomcat.util.ExceptionUtils;
import org.nhsrc.web.contract.ext.APIError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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
    public ResponseEntity handleException(Exception e, WebRequest req, HttpServletResponse res) {
        if (e instanceof GunakAPIException) {
            GunakAPIException gunakAPIException = (GunakAPIException) e;
            APIError apiError = createAPIError(gunakAPIException.getHttpStatusCode(), gunakAPIException.getMessage());
            logger.info(apiError.toString());
            return new ResponseEntity<>(apiError, gunakAPIException.getHttpStatusCode());
        } else if (e instanceof AccessDeniedException) {
            return handleException(e, HttpStatus.UNAUTHORIZED, "You are not authorised to access the API. Please contact support if you need help.");
        } else if (e instanceof InvalidDataAccessApiUsageException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "One or more request parameter is missing");
        } else if (e instanceof ConversionFailedException) {
            return handleException(e, HttpStatus.BAD_REQUEST, "Data type conversion failed. Possible reasons could be - date format being incorrect.");
        } else {
            Report report = bugsnag.buildReport(e);
            bugsnag.notify(report);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, sw.toString());
        }
    }

    public ResponseEntity handleException(Exception e, HttpStatus httpStatus, String message) {
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