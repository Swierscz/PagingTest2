package com.example.pagingtest2;

public class FetchDataStatus {
    public enum Status {
        RUNNING, FAILED, SUCCESS;
    }

    private final Status status;
    private final String message;

    public FetchDataStatus(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public static final FetchDataStatus LOADED_SUCCESSFULLY;
    public static final FetchDataStatus LOADING;

    static {
        LOADED_SUCCESSFULLY = new FetchDataStatus(Status.SUCCESS, "Success");
        LOADING = new FetchDataStatus(Status.RUNNING, "Running");
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}