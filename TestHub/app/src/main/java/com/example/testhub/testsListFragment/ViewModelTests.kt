package com.example.testhub.testsListFragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testhub.model.Test
import com.example.testhub.repository.Repository
import com.example.testhub.retrofit.response.TestInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ViewModelTests (
    private val repo: Repository
) : ViewModel() {
    private val _testsList = MutableStateFlow<List<Test>?>(emptyList())
    val testsList: StateFlow<List<Test>?> get() = _testsList

    private val _testInfo = MutableLiveData<TestInfo>()
    val testInfo: MutableLiveData<TestInfo> get() = _testInfo

    init{
        loadTests()
    }
    fun loadTests(){
        viewModelScope.launch {
            _testsList.value = repo.loadTests().orEmpty()
            Log.d("checkList", "${_testsList.value}")
        }
    }

    fun loadTestInfo(idTest: Long){
        viewModelScope.launch {
            _testInfo.value = repo.loadInfoTest(idTest)
        }
    }
}