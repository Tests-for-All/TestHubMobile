package com.example.testhub.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testhub.R

class LoginFragment : Fragment() {

    private var listener: goRegistration? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is goRegistration)
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

        view.findViewById<Button>(R.id.b_registration).setOnClickListener {
            listener?.openRegistrationLayout()
        }
    }

    companion object {
        fun newInstance() = LoginFragment()

        interface goRegistration{
            fun openRegistrationLayout()
        }
    }
}