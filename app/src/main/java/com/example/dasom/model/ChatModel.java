package com.example.dasom.model;

public class ChatModel {

    private String date, time, text;
    private Boolean isMine;

    public ChatModel(){}

    public ChatModel(String date, String time, String text, Boolean isMine) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.isMine = isMine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getMine() {
        return isMine;
    }

    public void setMine(Boolean mine) {
        isMine = mine;
    }
}
