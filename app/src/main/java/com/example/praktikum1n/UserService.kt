package com.example.praktikum1n

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {
    @GET("search/users")
    suspend fun getUser2(
        @Query("q") q: String
    ) : Response

    @GET("users/{username}")
    suspend fun getDetailUser2(
        @Path("username") username: String
    ) : UserItem
}