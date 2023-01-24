package com.example.demonotes.api

import com.example.demonotes.model.UserRequest
import com.example.demonotes.model.UserResponce
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("/users/signup")
    suspend fun signUp(@Body userRequest: UserRequest): Response<UserResponce>

     @POST("/users/signin")
    suspend fun signIn(@Body userRequest: UserRequest):Response<UserResponce>
}