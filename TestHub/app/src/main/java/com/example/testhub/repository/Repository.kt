package com.example.testhub.repository

import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.User

interface Repository {
    suspend fun registration(user: User): Boolean
    suspend fun authorization(user: LoginUser): Boolean

    suspend fun exampleRequest(): Boolean

}