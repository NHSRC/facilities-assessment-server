package org.nhsrc.web.framework;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class GunakBugsnagException extends RuntimeException {
    private GunakBugsnagException(String message, Throwable cause) {
        super(message, cause);
    }

    public static Exception create(ServletRequest request, Exception e) {
        try {
            String requestData = request.getReader().lines().collect(Collectors.joining());
            return new GunakBugsnagException(requestData, e);
        } catch (IOException e2) {
            return e;
        }
    }
}