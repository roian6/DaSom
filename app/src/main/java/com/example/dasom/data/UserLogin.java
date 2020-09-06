package com.example.dasom.data;

public class UserLogin {

    private int status;
    private String message;
    private String accessToken;
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

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserLogin(int status, String message, String accessToken, boolean success) {
        this.status = status;
        this.message = message;
        this.accessToken = accessToken;
        this.success = success;
    }
}
