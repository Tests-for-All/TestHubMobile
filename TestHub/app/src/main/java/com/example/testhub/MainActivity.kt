package com.example.testhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testhub.addTestFragment.AddTestFragment
import com.example.testhub.login.LoginFragment
import com.example.testhub.registration.RegistrationFragment
import com.example.testhub.fragments.StartMenu
import com.example.testhub.repository.Repository
import com.example.testhub.repository.RepositoryNetwork
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.NetworkModule
import com.example.testhub.retrofit.dataSource.RemoteDataSource
import com.example.testhub.testingFragment.TestingFragment
import com.example.testhub.testsListFragment.TestsListFragment

class MainActivity :
    AppCompatActivity(),
    StartMenu.Companion.StartFragmentInterface,
    LoginFragment.Companion.LoginFragmentInterface,
    RegistrationFragment.Companion.RegistrationFragmentInterface,
    TestsListFragment.Companion.TestFragmentInterface,
    AddTestFragment.Companion.AddTestInterface,
    TestingFragment.Companion.TestingInterface,
    RepositoryNetworkProvider {

    private val networkModule = NetworkModule()
    private val remoteDataSource = RemoteDataSource(networkModule.api, networkModule.auth)
    private val repo = RepositoryNetwork(remoteDataSource)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
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
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TestsListFragment())
            .commit()
    }
    override fun openAddTestLayout() {
        supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.fragment_container, AddTestFragment())
            .commit()
    }

    override fun openTestingFragment(testId: Long) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TestingFragment.create(testId))
            .commit()
    }

    override fun provideRepository(): Repository {
        return repo
    }

    override fun backToTestLayout() {
        val lastFragment = supportFragmentManager.fragments.last()
        supportFragmentManager.beginTransaction()
            .apply {
                remove(lastFragment)
                commit()
            }
    }
}