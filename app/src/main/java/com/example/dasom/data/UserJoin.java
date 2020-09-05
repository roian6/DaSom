package com.example.dasom.data;

public class UserJoin {
    int status;
    String message;
    String mes;
    boolean success;

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

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
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
        this.mes = mes;
        this.success = success;
    }
}
