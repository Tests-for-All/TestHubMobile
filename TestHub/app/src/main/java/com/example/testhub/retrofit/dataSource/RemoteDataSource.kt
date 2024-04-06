package com.example.testhub.retrofit.dataSource

import android.util.Log
import com.example.testhub.model.JWT
import com.example.testhub.model.User
import com.example.testhub.retrofit.ApiService
import com.example.testhub.retrofit.AuthInterceptor

class RemoteDataSource(
    private val api: ApiService,
    private val interceptor: AuthInterceptor
): RemoteDataSourceInterface
{
    override suspend fun registration(user: User) {
        val response = api.signUp(user)

        when(response.code()){
            200 -> {
                val jwt = JWT(response.body()?.token.orEmpty())
                Log.d("signUp", "${response.code()} ${jwt.token}")
                interceptor.updateToken(jwt.token)
            }
            else -> {
                Log.d("signUp", "${response.code()} ${response.message()}")
            }
        }
    }

    override suspend fun authorization(user: User) {
        /*TODO("Not yet implemented")*/
    }

}