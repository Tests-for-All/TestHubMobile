package com.example.testhub.retrofit.dataSource

import com.example.testhub.model.JWT
import com.example.testhub.model.User

interface RemoteDataSourceInterface {
    suspend fun registration(user: User)
    suspend fun authorization(user: User)
}