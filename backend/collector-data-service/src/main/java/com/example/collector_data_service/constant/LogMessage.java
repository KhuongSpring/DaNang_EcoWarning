package com.example.collector_data_service.constant;

public class LogMessage {

    private LogMessage() {
    }

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILED = "FAILED";

    // DatabaseInitializer
    public static final String ASSETS_ALREADY_INITIALIZED = "Assets data already initialized. Skipping.";
    public static final String OBSERVATIONS_ALREADY_INITIALIZED = "Observation data already initialized. Skipping.";
    public static final String DATABASE_CONNECTION_FAILED = "DATABASE CONNECTION FAILED. Cannot proceed with data initialization.";
    public static final String ASSETS_INIT_START = "Starting Assets initialization from CSV files...";
    public static final String ASSETS_INIT_FINISHED = "Assets initialization finished for run ID: {}";
    public static final String OBSERVATIONS_INIT_START = "Starting Observations initialization...";
    public static final String OBSERVATIONS_INIT_FINISHED = "Observations initialization finished for run ID: {}";
    public static final String MISSING_ASSET_OR_METRIC = "Skipping Kafka event for Observation ID: {} due to missing Asset or Metric";

    // safeParse
    public static final String FILE_PARSE_FAILED = "Failed to parse file {}: {}";
    public static final String FILE_NOT_FOUND_IN_RESOURCES = "File not found in resources: %s";
    public static final String LOAD_DOUBLE_FAIL = "Could not parse double value: '{}'";


}