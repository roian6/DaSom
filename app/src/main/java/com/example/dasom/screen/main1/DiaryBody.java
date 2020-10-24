package com.example.dasom.screen.main1;

import java.io.Serializable;
import java.util.List;

public class DiaryBody{


    private Integer status;
    private String message;
    private Boolean success;
    private List<DiaryData> data = null;
    private Integer dataLength;

    public DiaryBody() {
    }

    public DiaryBody(Integer status, String message, Boolean success, List<DiaryData> data, Integer dataLength) {
        super();
        this.status = status;
        this.message = message;
        this.success = success;
        this.data = data;
        this.dataLength = dataLength;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public List<DiaryData> getData() {
        return data;
    }

    public void setData(List<DiaryData> data) {
        this.data = data;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }


}
