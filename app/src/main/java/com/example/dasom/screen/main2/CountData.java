package com.example.dasom.screen.main2;

import java.util.HashMap;
import java.util.Map;

public class CountData {
    private Integer lastDataCount;
    private Integer dataLength;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getLastDataCount() {
        return lastDataCount;
    }

    public void setLastDataCount(Integer lastDataCount) {
        this.lastDataCount = lastDataCount;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
