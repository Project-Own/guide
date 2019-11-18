
package com.example.guide.Model.Forex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class USDTND {

    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("timestamp")
    @Expose
    private Integer timestamp;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}
