package com.compAndBen.exception;
public class ExceptionResponse {
    private String message;
    private String status;

    public ExceptionResponse(String errorMessage, String name, int value) {
    }

    @Override
    public String toString() {
        return "ExceptionResponse{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", statusCode=" + statusCode +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    private int statusCode;
   
}