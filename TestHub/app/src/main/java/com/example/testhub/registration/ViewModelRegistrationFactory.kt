package com.example.testhub.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class ViewModelRegistrationFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when (modelClass) {
        ViewModelRegistration::class.java -> ViewModelRegistration(
            RegistrationInteractor(
                Dispatchers.Default
            )
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}
