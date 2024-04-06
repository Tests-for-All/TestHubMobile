package com.example.testhub.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testhub.registration.RegistrationInteractor
import com.example.testhub.registration.ViewModelRegistration
import com.example.testhub.repository.Repository
import kotlinx.coroutines.Dispatchers

class ViewModelLoginFactory (
    private val repo: Repository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelLogin::class.java -> ViewModelLogin (repo)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}