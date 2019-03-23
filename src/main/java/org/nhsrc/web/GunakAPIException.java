package org.nhsrc.web;

public class GunakAPIException extends RuntimeException {
    private String errorCode;

    public static final String INVALID_PROGRAM_NAME = "INVALID_PROGRAM_NAME";
    public static final String INVALID_ASSESSMENT_TOOL_NAME = "INVALID_ASSESSMENT_TOOL_NAME";
    public static final String INVALID_ASSESSMENT_SYSTEM_ID = "INVALID_ASSESSMENT_SYSTEM_ID";

    public GunakAPIException(String errorCode) {
        this.errorCode = errorCode;
    }
}