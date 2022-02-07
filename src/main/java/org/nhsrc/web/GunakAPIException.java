package org.nhsrc.web;

import org.springframework.http.HttpStatus;

public class GunakAPIException extends RuntimeException {
    private HttpStatus httpStatusCode;

    public static final String INVALID_PROGRAM_UUID = "The system id of program didn't match any program in the system";
    public static final String INVALID_ASSESSMENT_TOOL_UUID = "The system id of assessment tool didn't match any assessment tool in the system";
    public static final String INVALID_ASSESSMENT_SYSTEM_ID = "The id of the assessment provided didn't match any assessment in the system";
    public static final String INVALID_ASSESSMENT_TOOL_SYSTEM_ID = "The id of the assessment tool provided didn't match any assessment tools in the system";
    public static final String INVALID_ASSESSMENT_TYPE = "The system id of assessment type didn't match any assessment types";
    public static final String INVALID_STATE = "The name of teh state didn't match any of the state names in the system";

    public GunakAPIException(String errorMessage, HttpStatus httpStatusCode) {
        super(errorMessage);
        this.httpStatusCode = httpStatusCode;
    }


    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }
}
