
package com.example.guide.Model.Currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CurrencyData {

    @SerializedName("results")
    @Expose
    private Results results;

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }


}
