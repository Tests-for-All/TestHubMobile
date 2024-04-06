package com.example.testhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testhub.fragments.LoginFragment
import com.example.testhub.registration.RegistrationFragment
import com.example.testhub.fragments.StartMenu
import com.example.testhub.repository.Repository
import com.example.testhub.repository.RepositoryNetwork
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.NetworkModule
import com.example.testhub.retrofit.dataSource.RemoteDataSource

class MainActivity :
    AppCompatActivity(),
    StartMenu.Companion.goLogin,
    LoginFragment.Companion.goRegistration,
    RepositoryNetworkProvider {


    private val networkModule = NetworkModule()
    private val remoteDataSource = RemoteDataSource(networkModule.api, networkModule.auth)
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

    override fun provideRepository(): Repository {
        return repo
    }
}