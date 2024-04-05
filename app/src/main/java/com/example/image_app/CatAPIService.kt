package com.example.image_app

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("v1/images/search")
    fun getCatImages(@Query("limit") limit: Int, @Query("page") page: Int): Call<List<CatImage>>
}
