package com.example.testhub.repository

internal interface RepositoryNetworkProvider {
    fun provideRepository(): Repository
}