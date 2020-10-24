package com.example.dasom.screen.splash;

public class CheckId {
    private int status;
    private String message;
    private boolean success;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CheckId(int status, String message, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }
}
