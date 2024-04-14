package com.example.testhub.addTestFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelAddTest(
    private val repo: Repository
) : ViewModel() {
    private val _tagsList = MutableStateFlow<List<Tag>?>(emptyList())
    val tagsList: StateFlow<List<Tag>?> get() = _tagsList

    private val _testStatus = MutableLiveData <State>(State.Default())
    val testStatus: LiveData<State> get() = _testStatus

    init {
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            _tagsList.value = repo.loadTags().orEmpty()
            Log.d("checkList", "${_tagsList.value}")
        }
    }

    fun saveTest(test: TestToAdd){
        viewModelScope.launch {
            _testStatus.value = State.Default()
            Log.d ("sendJson", test.toString())
            val answer = repo.saveTest(test)
            if(answer)
                _testStatus.value = State.Success()
            else
                _testStatus.value = State.Error()
        }
    }

    sealed class State {
        class Default : State()
        class Error: State()
        class Success : State()
    }
}