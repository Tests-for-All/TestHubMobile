package com.example.testhub.repository

import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.Test
import com.example.testhub.model.User
import com.example.testhub.retrofit.response.TestInfo
import retrofit2.Response

interface Repository {
    suspend fun registration(user: User): Boolean
    suspend fun authorization(user: LoginUser): Boolean
    suspend fun exampleRequest(): Boolean
    suspend fun loadTests(): List<Test>?
    suspend fun loadInfoTest(idTest: Long): TestInfo?
}