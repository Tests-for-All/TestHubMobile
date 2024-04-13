package com.example.testhub.testFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.repository.Repository
import kotlinx.coroutines.launch

class ViewModelTests (
    private val repo: Repository
) : ViewModel() {
    private val _state = MutableLiveData<State>(State.Default())
    val state: LiveData<State> get() = _state

    fun exampleRequest(){
        viewModelScope.launch {

            when (repo.exampleRequest()){
                true ->{
                    _state.value = State.Success()
                }
                false->{
                    _state.value = State.Error()
                }
            }
        }
    }

    sealed class State {
        class Default : State()
        class Error : State()

        class Success : State()
    }
}