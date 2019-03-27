package org.nhsrc.web.contract.ext;

import com.fasterxml.jackson.annotation.JsonInclude;

public class APIError {
    private int httpStatusCode;
    private String errorMessage;

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "httpStatusCode=" + httpStatusCode +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}