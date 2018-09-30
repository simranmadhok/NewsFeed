package com.example.sndtcsi.newsfeed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("top-headlines")
    Call<News> getArticles(@Query("sources") String source, @Query("apiKey") String apiKey);
}
