package com.example.dasom.model;

import com.example.dasom.data.ChatBody;
import com.google.gson.annotations.SerializedName;

public class ChatModel {

    private String date;
    private String time;
    private String text;
    private Boolean isMine;

    public ChatModel() {
    }

    public ChatModel(String date, String time, String text) {
        this.date = date;
        this.time = time;
        this.text = text;
        isMine = true;
    }

    public ChatModel(String date, String time, String text, Boolean isMine) {
        this.date = date;
        this.time = time;
        this.text = text;
        this.isMine = isMine;
    }

    public ChatModel(ChatBody body) {
        text = body.getText();
        isMine = false;
    }

    public ChatModel(String text, Boolean isMine){
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
