package com.example.testhub.registration

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.testhub.R
import com.example.testhub.login.LoginFragment
import com.example.testhub.repository.RepositoryNetwork
import com.example.testhub.repository.RepositoryNetworkProvider
import com.google.android.material.textfield.TextInputLayout

class RegistrationFragment : Fragment() {

    private val viewModel: ViewModelRegistration by viewModels {
        ViewModelRegistrationFactory((requireActivity() as RepositoryNetworkProvider).provideRepository())
    }

    private var usernameInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null
    private var repPasswordInputLayout: TextInputLayout? = null
    private var emailInputLayout: TextInputLayout? = null

    private var usernameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var repPasswordEditText: EditText? = null
    private var emailEditText: EditText? = null

    private var registrationButton: Button? = null

    private var listener: RegistrationFragmentInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is RegistrationFragmentInterface)
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
            is ViewModelRegistration.State.RegistrationError ->{
                Toast.makeText(requireActivity(), "Почта или имя пользователя существуют", Toast.LENGTH_SHORT).show()
            }
            is ViewModelRegistration.State.Loading ->{
                registrationButton?.isEnabled = false
            }
            is ViewModelRegistration.State.EmailError ->{
                emailInputLayout?.helperText = "Введите почту"
                registrationButton?.isEnabled = true
            }
            is ViewModelRegistration.State.PasswordError ->{
                passwordInputLayout?.helperText = "Введите пароль"
                registrationButton?.isEnabled = true
            }
            is ViewModelRegistration.State.RepPasswordError ->{
                repPasswordInputLayout?.helperText = "Пароли должны совпадать"
                registrationButton?.isEnabled = true
            }
            is ViewModelRegistration.State.UserNameError ->{
                usernameInputLayout?.helperText = "Введите имя пользователя"
                registrationButton?.isEnabled = true
            }
            is ViewModelRegistration.State.Success ->{
                Toast.makeText(requireActivity(), "Registration Success!!!", Toast.LENGTH_SHORT).show()
                registrationButton?.isEnabled = true
                listener?.openTestLayout()
            }
            is ViewModelRegistration.State.Default ->{
                usernameInputLayout?.helperText = null
                repPasswordInputLayout?.helperText = null
                passwordInputLayout?.helperText = null
                emailInputLayout?.helperText = null
                registrationButton?.isEnabled = true
            }
        }
    }
    companion object {
        fun newInstance() = RegistrationFragment()

        interface RegistrationFragmentInterface{
            fun openTestLayout()
        }
    }
}