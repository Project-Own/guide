
package com.example.guide.Model.Currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class YER {

    @SerializedName("currencyName")
    @Expose
    private String currencyName;
    @SerializedName("currencySymbol")
    @Expose
    private String currencySymbol;
    @SerializedName("id")
    @Expose
    private String id;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
