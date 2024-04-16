package com.example.testhub.testingFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testhub.repository.Repository
import com.example.testhub.testsListFragment.ViewModelTests

@Suppress("UNCHECKED_CAST")
class ViewModelTestingFactory (
    private val repo: Repository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelTesting::class.java -> ViewModelTesting (repo)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}