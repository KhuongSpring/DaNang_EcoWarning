package com.example.citizen_report_service.constant;

public class LogMessage {

    private LogMessage() {
    }

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";

    // Init Report Type
    public static final String REPORT_TYPE_ALREADY_INITIALIZED = "ReportTypes already initialized. Skipping...";
    public static final String REPORT_TYPE_INIT_START = "Starting inti report types.";
    public static final String REPORT_TYPE_INIT_FINISHED = "Finished inti report types.";
}