package com.example.testhub.testingFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.QuestionHidden
import com.example.testhub.model.TestToCheck
import com.example.testhub.repository.Repository
import com.example.testhub.retrofit.response.ResultTest
import com.example.testhub.retrofit.response.TestInfo
import kotlinx.coroutines.launch

class ViewModelTesting (
    private val repo: Repository
) : ViewModel() {
    private val _testInfo = MutableLiveData<TestInfo>()
    val testInfo: LiveData<TestInfo> get() = _testInfo

    private val _question = MutableLiveData<QuestionHidden>()
    val question: LiveData<QuestionHidden> get() = _question

    private val _checkTest = MutableLiveData<ResultTest?>()
    val checkTest : LiveData<ResultTest?> get() = _checkTest

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
            _checkTest.value = repo.checkTest(test)
        }
    }
}