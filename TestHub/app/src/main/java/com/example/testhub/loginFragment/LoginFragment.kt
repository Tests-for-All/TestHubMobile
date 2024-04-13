package com.example.testhub.loginFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.testhub.R
import com.example.testhub.repository.RepositoryNetworkProvider
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {

    private var listener: LoginFragmentInterface? = null

    private val viewModel: ViewModelLogin by viewModels {
        ViewModelLoginFactory((requireActivity() as RepositoryNetworkProvider).provideRepository())
    }

    private var emailInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null

    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null

    private var registrationButton: Button? = null
    private var loginButton: Button? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is LoginFragmentInterface)
            listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.login_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this.viewLifecycleOwner, this::setState)
        initViews(view)

        setUpListener()
    }

    private fun setUpListener() {
        registrationButton?.setOnClickListener {
            listener?.openRegistrationLayout()
        }

        loginButton?.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val email = emailEditText?.text?.toString().orEmpty()
        val password = passwordEditText?.text?.toString().orEmpty()

        viewModel.login(email, password)
    }

    private fun initViews(view: View) {
        registrationButton = view.findViewById(R.id.b_registration)
        loginButton = view.findViewById(R.id.b_authorization)

        passwordInputLayout = view.findViewById(R.id.passwordEditLayout)
        emailInputLayout = view.findViewById(R.id.emailEditLayout)

        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
    }

    private fun setState(state: ViewModelLogin.State){
        when(state){
            is ViewModelLogin.State.Loading -> {
                loginButton?.isEnabled = false
                registrationButton?.isEnabled = false
            }
            is ViewModelLogin.State.Default -> {
                loginButton?.isEnabled = true
                registrationButton?.isEnabled = true
            }
            is ViewModelLogin.State.LoginError -> {
                Toast.makeText(requireActivity(), "Неверный логин и/или пароль", Toast.LENGTH_SHORT).show()
            }
            is ViewModelLogin.State.Success -> {
                Toast.makeText(requireActivity(), "Вы зашли", Toast.LENGTH_SHORT).show()
                listener?.openTestLayout()
            }
        }
    }

    companion object {
        fun newInstance() = LoginFragment()

        interface LoginFragmentInterface{
            fun openRegistrationLayout()
            fun openTestLayout()
        }
    }
}