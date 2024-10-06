package com.example.praktikum1n

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("user")
    suspend fun getDetailUser2() : Response<UserItem>
}