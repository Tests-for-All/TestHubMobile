package com.example.testhub.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ViewModelRegistration(
    private val interactor: RegistrationInteractor
) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _state

    fun registration(userName: String, password: String, repPassword: String, email: String) {
        viewModelScope.launch {

            //_state.value = State.Loading()
            val registrationResult = interactor.registration(userName, password, repPassword ,email)

            _state.value  = when(registrationResult){
                is RegistrationInteractor.RegistrationResult.Error.Password -> State.PasswordError()
                is RegistrationInteractor.RegistrationResult.Error.UserName -> State.UserNameError()
                is RegistrationInteractor.RegistrationResult.Error.Email -> State.EmailError()
                is RegistrationInteractor.RegistrationResult.Error.RepPassword -> State.RepPasswordError()
                else -> State.Success()
            }
        }
    }

    sealed class State {
        class Default : State()
        //class Loading : State()

        class EmailError : State()
        class UserNameError : State()
        class PasswordError : State()
        class RepPasswordError : State()
        class Success : State()
    }
}