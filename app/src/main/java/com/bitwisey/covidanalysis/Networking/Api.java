package com.bitwisey.covidanalysis.Networking;

import com.bitwisey.covidanalysis.Models.Countries;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {
    //   @GET("summary")
    @GET("countries")
    Call<List<Countries>> getCases();


}