package com.example.testhub.retrofit

import com.example.testhub.model.LoginUser
import com.example.testhub.model.Test
import com.example.testhub.model.User
import com.example.testhub.retrofit.response.JwtResponse
import com.example.testhub.retrofit.response.TestInfo
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/sign-up")
    suspend fun signUp(@Body user: User): Response<JwtResponse>

    @POST("auth/sign-in")
    suspend fun signIn(@Body user: LoginUser): Response<JwtResponse>

    @GET("example")
    suspend fun example(): Response<ResponseBody>

    @GET("api/v1/tests/all")
    suspend fun loadTests(): Response<List<Test>>

    @GET("api/v1/tests/{id}")
    suspend fun loadInfoTest(@Path("id") idTest: Long): Response<TestInfo>
}