package com.example.testhub.retrofit

import android.util.Log
import okhttp3.Interceptor
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

object NetworkModule {

    val auth = AuthInterceptor(null)
    private var client = OkHttpClient.Builder()
        .addInterceptor(auth)
        .build()

    private const val ip = "192.168.43.169" /*46.101.86.38*/
    private const val baseUrl = "http://$ip:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api: ApiService by lazy{ retrofit.create(ApiService::class.java)}
}

class AuthInterceptor(private var authToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()

        Log.d("Check token","$authToken" )
        // Проверяем тип запроса
        if (isAuthRequest(originalRequest)) {
            return chain.proceed(originalRequest)
        }

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $authToken")
            .build()
        return chain.proceed(newRequest)
    }

    private fun isAuthRequest(request: Request): Boolean {
        val path = request.url.encodedPath
        return path.startsWith("/auth/")
    }

    fun updateToken(newToken: String?) {
        authToken = newToken.orEmpty()
    }
}