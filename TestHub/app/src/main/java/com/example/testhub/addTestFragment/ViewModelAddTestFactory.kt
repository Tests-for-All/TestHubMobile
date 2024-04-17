package com.example.testhub.addTestFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testhub.repository.Repository

class ViewModelAddTestFactory (
    private val repo: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelAddTest::class.java -> ViewModelAddTest (repo)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}