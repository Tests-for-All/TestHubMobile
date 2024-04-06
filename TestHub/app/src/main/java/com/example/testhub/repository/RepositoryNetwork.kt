package com.example.testhub.repository

import com.example.testhub.model.JWT
import com.example.testhub.model.User
import com.example.testhub.retrofit.dataSource.RemoteDataSource

class RepositoryNetwork(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override suspend fun registration(user: User) {
        remoteDataSource.registration(user)
    }

    override suspend fun authorization(user: User) {
        remoteDataSource.authorization(user)
    }
}