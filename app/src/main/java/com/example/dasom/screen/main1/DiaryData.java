package com.example.dasom.screen.main1;

import com.example.dasom.model.ChatModel;

import java.util.List;

public class DiaryData {
    
    private List<ChatModel> data = null;
    private String _id;
    private String owner;
    private String date;
    private Integer __v;

    public DiaryData() {
    }
    
    public DiaryData(List<ChatModel> data, String _id, String owner, String date, Integer __v) {
        super();
        this.data = data;
        this._id = _id;
        this.owner = owner;
        this.date = date;
        this.__v = __v;
    }

    public List<ChatModel> getData() {
        return data;
    }

    public void setData(List<ChatModel> data) {
        this.data = data;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer get__v() {
        return __v;
    }

    public void set__v(Integer __v) {
        this.__v = __v;
    }


}
