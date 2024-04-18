package com.example.testhub.retrofit.dataSource

import android.util.Log
import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.PageCriteria
import com.example.testhub.model.QuestionHidden
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.model.TestToCheck
import com.example.testhub.model.User
import com.example.testhub.retrofit.ApiService
import com.example.testhub.retrofit.AuthInterceptor
import com.example.testhub.retrofit.response.PageTest
import com.example.testhub.retrofit.response.ResultTest
import com.example.testhub.retrofit.response.TestInfo

class RemoteDataSource(
    private val api: ApiService,
    private val interceptor: AuthInterceptor
): RemoteDataSourceInterface
{
    override suspend fun registration(user: User): Boolean {
        val response = api.signUp(user)

        return when(response.code()){
            200 -> {
                val jwt = JWT(response.body()?.token.orEmpty())
                Log.d("signUp", "${response.code()} ${jwt.token}")
                interceptor.updateToken(jwt.token)
                true
            }

            else -> {
                Log.d("signUp", "${response.code()} ${response.message()}")
                false
            }
        }
    }

    override suspend fun authorization(user: LoginUser): Boolean {
        val response = api.signIn(user)
        Log.d("signIn", "${response.code()}")
        return when(response.code()){
            200 -> {
                val jwt = JWT(response.body()?.token.orEmpty())
                Log.d("signIn", "${response.code()} ${jwt.token}")
                interceptor.updateToken(jwt.token)
                true
            }

            else -> {
                Log.d("signIn", "${response.code()} ${response.message()}")
                false
            }
        }
    }

    override suspend fun loadTests(): List<Test>? {
        val response = api.loadAllTests()

        return when(response.code()){
            200 -> {
                return response.body()
            }
            else -> null
        }
    }

    override suspend fun loadInfoTest(idTest: Long): TestInfo? {
        val response = api.loadInfoTest(idTest)

        return when(response.code()){
            200 -> {
                response.body()
            }
            else -> null
        }
    }

    override suspend fun loadTags(): List<Tag>? {
        val response = api.loadTags()

        return when(response.code()){
            200 -> response.body()
            else -> null
        }

    }

    override suspend fun saveTest(test: TestToAdd): Boolean{
        val response = api.saveTest(test)
        Log.d("testToSave", test.toString())
        Log.d("saveTestResp", response.code().toString())

        return when(response.code()){
            201 -> true
            else -> false
        }
    }

    override suspend fun loadQuestion(idQuestion: Long): QuestionHidden? {
        val response = api.loadQuestion(idQuestion)

        Log.d("CheckQuestion", response.body().toString())

        return when(response.code()){
            200 -> response.body()
            else -> null
        }
    }

    override suspend fun checkTest(test: TestToCheck): ResultTest? {
        val response = api.checkTest(test)

        Log.d("CheckCheckTest", response.body().toString())

        return when(response.code()){
            201 -> response.body()
            else -> null
        }
    }

    override suspend fun loadPageTest(page: Long, pageSize: Int, criteria : PageCriteria): PageTest?{
        val response = api.loadPageTests(page, pageSize,criteria)
        return when(response.code()){
            200 -> {
                Log.d("checkPage", response.body().toString())
                response.body()
            }
            else -> null
        }
    }
}