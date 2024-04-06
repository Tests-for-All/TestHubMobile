package com.example.testhub.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.testhub.R

class StartMenu : Fragment() {

    private var listener: StartFragmentInterface? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is StartFragmentInterface)
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
        return inflater.inflate(R.layout.start_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startButton: Button = view.findViewById(R.id.start_b)
        startButton.setOnClickListener {
            listener?.openLoginLayout()
        }
    }

    companion object {
        fun newInstance(sharedPrefs: SharedPreferences?) = StartMenu()

        interface StartFragmentInterface{
            fun openLoginLayout()
        }
    }
}