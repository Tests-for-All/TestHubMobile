package com.example.testhub.repository

import com.example.testhub.model.JWT
import com.example.testhub.model.User

interface Repository {
    suspend fun registration(user: User)
    suspend fun authorization(user: User)

}