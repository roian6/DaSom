package com.example.dasom.screen.register;

public class UserJoin {

    private int status;
    private String message;
    boolean success;

    public UserJoin(){}

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

    public UserJoin(int status, String message, String mes, boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }
}
