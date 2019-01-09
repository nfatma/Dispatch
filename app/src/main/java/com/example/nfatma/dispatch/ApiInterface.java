package com.example.nfatma.dispatch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines?apiKey=bfd20cc2cf1543c89fa71e218f820d60")
    Call<NewsModelClass> getResults1(@Query("country") String country);

    @GET("everything?apiKey=bfd20cc2cf1543c89fa71e218f820d60")
    Call<NewsModelClass> getResults2(@Query("q") String q);

}
