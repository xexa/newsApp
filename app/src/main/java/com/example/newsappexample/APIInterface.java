package com.example.newsappexample;

import com.example.newsappexample.Model.NewsApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("top-headlines")
    Call<NewsApiResponse> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey);

    @GET("everything")
    Call<NewsApiResponse> searchNews(
            @Query("q") String query,
            @Query("apiKey") String apiKey);

}
