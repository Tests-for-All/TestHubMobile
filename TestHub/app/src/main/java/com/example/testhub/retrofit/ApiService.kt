package com.example.testhub.retrofit

import com.example.testhub.model.LoginUser
import com.example.testhub.model.User
import com.example.testhub.retrofit.response.JwtResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("auth/sign-up")
    suspend fun signUp(@Body user: User): Response<JwtResponse>

    @POST("auth/sign-in")
    suspend fun signIn(@Body user: LoginUser): Response<JwtResponse>

    @GET("example")
    suspend fun example(): Response<ResponseBody>
}