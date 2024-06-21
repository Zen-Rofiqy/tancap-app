package com.hikam.tancap.data.api

import android.media.Image
import com.hikam.tancap.data.response.Catalog
import com.hikam.tancap.data.response.LoginResponse
import com.hikam.tancap.data.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import com.hikam.tancap.data.response.NewsResponse
import retrofit2.http.GET

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("category") category: String = "health",
        @Query("apiKey") apiKey: String = "51ea2a4872f24b6d9d6a0dbd36ea21cd"
    ): NewsResponse

    @GET("catalog")
    suspend fun getCatalog(): List<Catalog>
}
