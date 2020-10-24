package com.example.dasom.screen.chat;


import retrofit2.http.Body;

public class ChatBody {

    private Integer status;
    private String action;
    private String text;
    private String message;
    private Boolean success;

    public ChatBody() {
    }

    public ChatBody(String message, Boolean success){
        this.message = message;
        this.success = success;
    }

    public ChatBody(Integer status, String action, String text, String message, Boolean success) {
        this.status = status;
        this.action = action;
        this.text = text;
        this.message = message;
        this.success = success;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

}

