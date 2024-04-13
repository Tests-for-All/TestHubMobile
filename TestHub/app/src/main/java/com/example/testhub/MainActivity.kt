package com.example.testhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testhub.loginFragment.LoginFragment
import com.example.testhub.registrationFragment.RegistrationFragment
import com.example.testhub.fragments.StartMenu
import com.example.testhub.repository.Repository
import com.example.testhub.repository.RepositoryNetwork
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.NetworkModule
import com.example.testhub.retrofit.dataSource.RemoteDataSource
import com.example.testhub.testFragment.TestFragment

class MainActivity :
    AppCompatActivity(),
    StartMenu.Companion.StartFragmentInterface,
    LoginFragment.Companion.LoginFragmentInterface,
    RegistrationFragment.Companion.RegistrationFragmentInterface,
    RepositoryNetworkProvider {


    private val remoteDataSource = RemoteDataSource(NetworkModule.api, NetworkModule.auth)
    private val repo = RepositoryNetwork(remoteDataSource)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, StartMenu())
                .commit()
    }

    override fun openLoginLayout() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    override fun openRegistrationLayout() {
        supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.fragment_container, RegistrationFragment())
            .commit()
    }

    override fun openTestLayout() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TestFragment())
            .commit()
    }

    override fun provideRepository(): Repository {
        return repo
    }
}