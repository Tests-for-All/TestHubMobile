package com.example.testhub

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testhub.fragments.LoginFragment
import com.example.testhub.fragments.RegistrationFragment
import com.example.testhub.fragments.StartMenu

class MainActivity :
    AppCompatActivity(),
    StartMenu.Companion.goLogin,
    LoginFragment.Companion.goRegistration {

    private val USER_LIST = "USER_LIST"
    private val USER_KEY = "USER_KEY"
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        sharedPref = getSharedPreferences(USER_LIST, MODE_PRIVATE)
        val users = sharedPref?.getString(USER_KEY, null)
        if(users == null){
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, StartMenu())
                .commit()
        }
    }

    override fun openLoginLayout() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, LoginFragment())
            .commit()
    }

    override fun openRegistrationLayout() {
        supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.fragment_container, RegistrationFragment())
            .commit()
    }
}