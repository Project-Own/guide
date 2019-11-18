
package com.example.guide.Model.Forex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class USDCUC {

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

}
