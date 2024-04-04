package com.example.testhub.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testhub.R
import com.google.android.material.textfield.TextInputLayout

class RegistrationFragment : Fragment() {

    private val viewModel: ViewModelRegistration by viewModels { ViewModelRegistrationFactory() }

    private var usernameInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null
    private var repPasswordInputLayout: TextInputLayout? = null
    private var emailInputLayout: TextInputLayout? = null

    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var repPasswordEditText: EditText? = null
    private var emailEditText: EditText? = null

    private var registrationButton: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.registration_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this.viewLifecycleOwner, this::setState)
        initViews(view)

        setUpListener()
    }

    private fun setUpListener() {
        registrationButton?.setOnClickListener {
            registration()
        }
    }

    private fun registration(){
        val username = usernameEditText?.text?.toString().orEmpty()
        val email = emailEditText?.text?.toString().orEmpty()
        val password = passwordEditText?.text?.toString().orEmpty()
        val repPassword = repPasswordEditText?.text?.toString().orEmpty()

        viewModel.registration(username, password, repPassword, email)
    }

    private fun initViews(view: View) {
        usernameInputLayout = view.findViewById(R.id.usernameEditLayout)
        passwordInputLayout = view.findViewById(R.id.passwordEditLayout)
        repPasswordInputLayout = view.findViewById(R.id.repPasswordEditLayout)
        emailInputLayout = view.findViewById(R.id.emailEditLayout)

        usernameEditText = view.findViewById(R.id.usernameEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        repPasswordEditText = view.findViewById(R.id.repPasswordEditText)
        emailEditText = view.findViewById(R.id.emailEditText)

        registrationButton = view.findViewById(R.id.b_registration)
    }


    private fun setState(state: ViewModelRegistration.State){
        when(state){
            is ViewModelRegistration.State.EmailError ->{
                emailInputLayout?.helperText = "Введите почту"
            }
            is ViewModelRegistration.State.PasswordError ->{
                passwordInputLayout?.helperText = "Введите пароль"
            }
            is ViewModelRegistration.State.RepPasswordError ->{
                repPasswordInputLayout?.helperText = "Пароли должны совпадать"
            }
            is ViewModelRegistration.State.UserNameError ->{
                usernameInputLayout?.helperText = "Введите имя пользователя"
            }
            is ViewModelRegistration.State.Success ->{
                usernameInputLayout?.helperText = null
                repPasswordInputLayout?.helperText = null
                passwordInputLayout?.helperText = null
                emailInputLayout?.helperText = null
            }
            is ViewModelRegistration.State.Default ->{
                usernameInputLayout?.helperText = null
                repPasswordInputLayout?.helperText = null
                passwordInputLayout?.helperText = null
                emailInputLayout?.helperText = null
            }
        }
    }
    companion object {
        fun newInstance() = RegistrationFragment()
    }
}