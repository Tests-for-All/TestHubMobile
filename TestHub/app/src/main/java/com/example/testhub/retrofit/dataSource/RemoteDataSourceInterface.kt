package com.example.testhub.retrofit.dataSource

import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.model.User
import com.example.testhub.retrofit.response.TestInfo
import retrofit2.Response

interface RemoteDataSourceInterface {
    suspend fun registration(user: User): Boolean
    suspend fun authorization(user: LoginUser): Boolean
    suspend fun exampleRequest(): Boolean
    suspend fun loadTests(): List<Test>?
    suspend fun loadInfoTest(idTest: Long): TestInfo?
    suspend fun loadTags(): List<Tag>?

    suspend fun saveTest(test: TestToAdd): Boolean
}