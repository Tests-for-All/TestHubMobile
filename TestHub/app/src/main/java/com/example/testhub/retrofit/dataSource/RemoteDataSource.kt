package com.example.testhub.retrofit.dataSource

import android.util.Log
import com.example.testhub.model.JWT
import com.example.testhub.model.LoginUser
import com.example.testhub.model.QuestionGet
import com.example.testhub.model.Tag
import com.example.testhub.model.Test
import com.example.testhub.model.TestToAdd
import com.example.testhub.model.User
import com.example.testhub.retrofit.ApiService
import com.example.testhub.retrofit.AuthInterceptor
import com.example.testhub.retrofit.response.TestInfo
import retrofit2.Response

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

    override suspend fun exampleRequest(): Boolean {
        val response = api.example()
        return when(response.code()){
            200 -> true
            else -> false
        }


    }
    override suspend fun loadTests(): List<Test>? {
        val response = api.loadTests()

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

    override suspend fun loadQuestion(idQuestion: Long): QuestionGet? {
        val response = api.loadQuestion(idQuestion)

        Log.d("CheckQuestion", response.body().toString())

        return when(response.code()){
            200 -> response.body()
            else -> null
        }
    }

}