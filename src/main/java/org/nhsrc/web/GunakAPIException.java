package org.nhsrc.web;

import org.springframework.http.HttpStatus;

public class GunakAPIException extends RuntimeException {
    private HttpStatus httpStatusCode;

    public static final String INVALID_PROGRAM_NAME = "The name of program didn't match any program name in the system";
    public static final String INVALID_ASSESSMENT_TOOL_NAME = "The name of assessment tool didn't match any assessment tool name in the system within the program";
    public static final String INVALID_ASSESSMENT_SYSTEM_ID = "The id of the assessment provided didn't match any assessment in the system";

    public GunakAPIException(String errorMessage, HttpStatus httpStatusCode) {
        super(errorMessage);
        this.httpStatusCode = httpStatusCode;
    }


    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}