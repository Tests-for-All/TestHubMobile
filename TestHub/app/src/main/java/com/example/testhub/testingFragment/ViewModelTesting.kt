package com.example.testhub.testingFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.QuestionHidden
import com.example.testhub.model.TestToCheck
import com.example.testhub.repository.Repository
import com.example.testhub.retrofit.response.TestInfo
import kotlinx.coroutines.launch

class ViewModelTesting (
    private val repo: Repository
) : ViewModel() {
    private val _testInfo = MutableLiveData<TestInfo>()
    val testInfo: LiveData<TestInfo> get() = _testInfo

    private val _question = MutableLiveData<QuestionHidden>()
    val question: LiveData<QuestionHidden> get() = _question

    private val _checkTest = MutableLiveData<State>(State.Default())
    val checkTest : LiveData<State> get() = _checkTest

    fun loadTestInfo(idTest: Long){
        viewModelScope.launch {
            _testInfo.value = repo.loadInfoTest(idTest)
        }
    }

    fun loadQuestion(idQuestion: Long){
        viewModelScope.launch {
            _question.value = repo.loadQuestion(idQuestion)
        }
    }

    fun checkTest(test: TestToCheck){
        viewModelScope.launch {
            val ans = repo.checkTest(test)
            if(ans){
                _checkTest.value = State.Success()
            }
            else
                _checkTest.value = State.Error()
        }
    }

    sealed class State {
        class Default : State()
        class Success : State()
        class Error : State()
    }

}