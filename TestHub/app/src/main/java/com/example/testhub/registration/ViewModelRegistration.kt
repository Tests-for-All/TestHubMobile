package com.example.testhub.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.User
import com.example.testhub.repository.Repository
import kotlinx.coroutines.launch

class ViewModelRegistration(
    private val interactor: RegistrationInteractor,
    private val repo: Repository
) : ViewModel() {

    private val _state = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _state

    fun validation(userName: String, password: String, repPassword: String, email: String) {
        viewModelScope.launch {

            val registrationResult = interactor.validation(userName, password, repPassword ,email)

            Log.d("ViewModel", state.value.toString())
            _state.value  = when(registrationResult){
                is RegistrationInteractor.RegistrationResult.Error.Password -> State.PasswordError()
                is RegistrationInteractor.RegistrationResult.Error.UserName -> State.UserNameError()
                is RegistrationInteractor.RegistrationResult.Error.Email -> State.EmailError()
                is RegistrationInteractor.RegistrationResult.Error.RepPassword -> State.RepPasswordError()
                else -> State.Success()
            }

            if(_state.value is State.Success){
                _state.value = State.Loading()
                Log.d("ViewModel", state.value.toString())
                repo.registration(User(
                    userName, email, password
                ))
                _state.value = State.Default()
            }
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()

        class EmailError : State()
        class UserNameError : State()
        class PasswordError : State()
        class RepPasswordError : State()
        class Success : State()
    }
}