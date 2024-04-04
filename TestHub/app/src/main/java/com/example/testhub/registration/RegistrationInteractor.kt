package com.example.testhub.registration

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RegistrationInteractor (private val dispatcher: CoroutineDispatcher) {

    suspend fun registration(userName: String, password: String, repPassword: String, email: String):  RegistrationResult =
        withContext(dispatcher) {

            when {
                userName.isEmpty() ->  RegistrationResult.Error.UserName()
                email.isEmpty() ->  RegistrationResult.Error.Email()
                password.isEmpty() ->  RegistrationResult.Error.Password()
                password != repPassword -> RegistrationResult.Error.RepPassword()
                else ->  RegistrationResult.Success()
            }
        }

    sealed class RegistrationResult {

        class Success :  RegistrationResult()

        sealed class Error :  RegistrationResult() {

            class UserName : Error()

            class Password : Error()
            class RepPassword : Error()
            class Email : Error()
        }
    }
}