
package com.example.guide.Modal.Forex;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ForexData {

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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rates", rates).append("code", code).toString();
    }

}
