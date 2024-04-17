package com.example.testhub.repository

import com.example.testhub.model.LoginUser
import com.example.testhub.model.QuestionHidden
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.model.TestToCheck
import com.example.testhub.model.User
import com.example.testhub.retrofit.response.TestInfo

interface Repository {
    suspend fun registration(user: User): Boolean
    suspend fun authorization(user: LoginUser): Boolean
    suspend fun exampleRequest(): Boolean
    suspend fun loadTests(): List<Test>?
    suspend fun loadInfoTest(idTest: Long): TestInfo?
    suspend fun loadTags(): List<Tag>?
    suspend fun saveTest(test: TestToAdd): Boolean
    suspend fun loadQuestion(idQuestion: Long): QuestionHidden?

    suspend fun checkTest(test: TestToCheck): Boolean
}