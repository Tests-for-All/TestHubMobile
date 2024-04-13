package com.example.testhub.registrationFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testhub.repository.Repository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class ViewModelRegistrationFactory(
    private val repo: Repository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelRegistration::class.java -> ViewModelRegistration(
            RegistrationInteractor(
                Dispatchers.Default
            ),
            repo
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
