package com.example.protobuftask.network

import com.example.protobuftask.model.UserResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUser(): Response<List<UserResponseItem>>
}