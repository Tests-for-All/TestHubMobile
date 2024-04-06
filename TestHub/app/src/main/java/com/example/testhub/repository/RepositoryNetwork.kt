package com.example.testhub.repository

import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.User
import com.example.testhub.retrofit.dataSource.RemoteDataSource

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
}