package com.example.testhub.retrofit.dataSource

import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.User

interface RemoteDataSourceInterface {
    suspend fun registration(user: User): Boolean
    suspend fun authorization(user: LoginUser): Boolean

    suspend fun exampleRequest(): Boolean
}