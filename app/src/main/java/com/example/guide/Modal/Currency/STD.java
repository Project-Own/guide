
package com.example.guide.Modal.Currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class STD {

    @SerializedName("currencyName")
    @Expose
    private String currencyName;
    @SerializedName("id")
    @Expose
    private String id;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("currencyName", currencyName).append("id", id).toString();
    }

}
