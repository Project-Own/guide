
package com.example.guide.Model.Forex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forex {

    @SerializedName("rates")
    @Expose
    private Rates rates;
    @SerializedName("code")
    @Expose
    private Integer code;

    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
