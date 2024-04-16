package com.example.testhub.testingFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.QuestionGet
import com.example.testhub.model.Test
import com.example.testhub.repository.Repository
import com.example.testhub.retrofit.response.TestInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelTesting (
    private val repo: Repository
) : ViewModel() {
    private val _testInfo = MutableLiveData<TestInfo>()
    val testInfo: LiveData<TestInfo> get() = _testInfo

    private val _question = MutableLiveData<QuestionGet>()
    val question: LiveData<QuestionGet> get() = _question

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



}