package com.example.testhub.retrofit

import com.example.testhub.model.LoginUser
import com.example.testhub.model.PageCriteria
import com.example.testhub.model.QuestionHidden
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.model.TestToCheck
import com.example.testhub.model.User
import com.example.testhub.retrofit.response.JwtResponse
import com.example.testhub.retrofit.response.PageTest
import com.example.testhub.retrofit.response.ResultTest
import com.example.testhub.retrofit.response.TestInfo
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth/sign-up")
    suspend fun signUp(@Body user: User): Response<JwtResponse>

    @POST("auth/sign-in")
    suspend fun signIn(@Body user: LoginUser): Response<JwtResponse>
    @GET("api/v1/tests/all")
    suspend fun loadAllTests(): Response<List<Test>>

    @POST("/api/v1/tests/search")
    suspend fun loadPageTests(@Query("page") page: Long,
                              @Query("pageSize") pageSize: Int,
                              @Body criteria : PageCriteria): Response<PageTest>

    @GET("api/v1/tests/{id}")
    suspend fun loadInfoTest(@Path("id") idTest: Long): Response<TestInfo>
    @GET("api/v1/tags/all")
    suspend fun loadTags(): Response<List<Tag>>

    @POST("api/v1/tests/")
    suspend fun saveTest(@Body test: TestToAdd): Response<ResponseBody>

    @GET("api/v1/questions/hidden/{id}")
    suspend fun loadQuestion(@Path("id") idQuestion: Long): Response<QuestionHidden>

    @POST("api/v1/test-results/")
    suspend fun checkTest(@Body test: TestToCheck): Response<ResultTest>
}