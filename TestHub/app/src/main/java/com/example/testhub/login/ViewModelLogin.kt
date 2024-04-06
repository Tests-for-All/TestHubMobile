package com.example.testhub.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.LoginUser
import com.example.testhub.registration.RegistrationInteractor
import com.example.testhub.registration.ViewModelRegistration
import com.example.testhub.repository.Repository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModelLogin (
    private val repo: Repository
) : ViewModel(){

    private val _state = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _state

    fun login(username: String, password: String){
        viewModelScope.launch {
            _state.value = State.Loading()

            val response = repo.authorization(LoginUser(username, password))

            if(!response)
                _state.value = State.LoginError()
            else
                _state.value = State.Success()
            _state.value = State.Default()
        }
    }

    sealed class State {
        class Default : State()
        class Loading : State()

        class LoginError : State()

        class Success : State()
    }
}