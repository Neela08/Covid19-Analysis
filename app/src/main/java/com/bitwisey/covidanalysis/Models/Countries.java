package com.bitwisey.covidanalysis.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countries {


    @SerializedName("country")
    @Expose
    private String Country;

    @SerializedName("cases")
    @Expose
    private int TotalConfirmed;

    @SerializedName("todayCases")
    @Expose
    private int NewConfirmed;

    @SerializedName("todayDeaths")
    @Expose
    private int NewDeaths;

    @SerializedName("deaths")
    @Expose
    private int TotalDeaths;


    @SerializedName("active")
    @Expose
    private int active;

    @SerializedName("recovered")
    @Expose
    private int TotalRecovered;


    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public int getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(int NewConfirmed) {
        this.NewConfirmed = NewConfirmed;
    }

    public int getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(int TotalConfirmed) {
        this.TotalConfirmed = TotalConfirmed;
    }

    public int getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(int NewDeaths) {
        this.NewDeaths = NewDeaths;
    }

    public int getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(int TotalDeaths) {
        this.TotalDeaths = TotalDeaths;
    }

    public int getactive() {
        return active;
    }

    public void setactive(int active) {
        this.active = active;
    }

    public int getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(int TotalRecovered) {
        this.TotalRecovered = TotalRecovered;
    }




}
