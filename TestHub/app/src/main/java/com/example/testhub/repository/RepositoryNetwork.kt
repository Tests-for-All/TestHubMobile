package com.example.testhub.repository

import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.QuestionGet
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.model.User
import com.example.testhub.retrofit.dataSource.RemoteDataSource
import com.example.testhub.retrofit.response.TestInfo
import retrofit2.Response

class RepositoryNetwork(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override suspend fun registration(user: User): Boolean {
        return remoteDataSource.registration(user)
    }

    override suspend fun authorization(user: LoginUser): Boolean {
        return remoteDataSource.authorization(user)
    }

    override suspend fun exampleRequest(): Boolean {
        return remoteDataSource.exampleRequest()
    }

    override suspend fun loadTests(): List<Test>?{
        return remoteDataSource.loadTests()
    }

    override suspend fun loadInfoTest(idTest: Long): TestInfo? {
        return remoteDataSource.loadInfoTest(idTest)
    }

    override suspend fun loadTags(): List<Tag>? {
        return remoteDataSource.loadTags()
    }

    override suspend fun saveTest(test: TestToAdd): Boolean{
        return remoteDataSource.saveTest(test)
    }

    override suspend fun loadQuestion(idQuestion: Long): QuestionGet? {
        return remoteDataSource.loadQuestion(idQuestion)
    }
}