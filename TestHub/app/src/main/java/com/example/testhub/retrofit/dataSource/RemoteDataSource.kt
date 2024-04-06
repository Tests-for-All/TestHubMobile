package com.example.testhub.retrofit.dataSource

import android.util.Log
import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.User
import com.example.testhub.retrofit.ApiService
import com.example.testhub.retrofit.AuthInterceptor

class RemoteDataSource(
    private val api: ApiService,
    private val interceptor: AuthInterceptor
): RemoteDataSourceInterface
{
    override suspend fun registration(user: User): Boolean {
        val response = api.signUp(user)

        return when(response.code()){
            200 -> {
                val jwt = JWT(response.body()?.token.orEmpty())
                Log.d("signUp", "${response.code()} ${jwt.token}")
                interceptor.updateToken(jwt.token)
                true
            }

            else -> {
                Log.d("signUp", "${response.code()} ${response.message()}")
                false
            }
        }
    }

    override suspend fun authorization(user: LoginUser): Boolean {
        val response = api.signIn(user)

        return when(response.code()){
            200 -> {
                val jwt = JWT(response.body()?.token.orEmpty())
                Log.d("signIn", "${response.code()} ${jwt.token}")
                interceptor.updateToken(jwt.token)
                true
            }

            else -> {
                Log.d("signIn", "${response.code()} ${response.message()}")
                false
            }
        }
    }

    override suspend fun exampleRequest(): Boolean {
        val response = api.example()
        return when(response.code()){
            200 -> true
            else -> false
        }


    }

}