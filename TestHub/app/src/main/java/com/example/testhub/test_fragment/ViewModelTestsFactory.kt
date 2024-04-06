package com.example.testhub.test_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testhub.registration.RegistrationInteractor
import com.example.testhub.registration.ViewModelRegistration
import com.example.testhub.repository.Repository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class ViewModelTestsFactory (
    private val repo: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelTests::class.java -> ViewModelTests (repo)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}