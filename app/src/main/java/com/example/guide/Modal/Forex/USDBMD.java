
package com.example.guide.Modal.Forex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class USDBMD {

    @SerializedName("rate")
    @Expose
    private Integer rate;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rate", rate).append("timestamp", timestamp).toString();
    }

}
