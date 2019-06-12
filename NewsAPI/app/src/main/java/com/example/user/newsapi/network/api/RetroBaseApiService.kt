package com.example.user.newsapi.network.api

import com.example.user.newsapi.network.dto.GetResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroBaseApiService {

    var baseUrl: String
        get() = "https://newsapi.org"
        set(value) = TODO()

    @GET("/v2/top-headlines")
    fun getResponse(@Query("country") country: String, @Query("category") category: String?, @Query("apiKey") apiKey: String): Observable<GetResponse>
}