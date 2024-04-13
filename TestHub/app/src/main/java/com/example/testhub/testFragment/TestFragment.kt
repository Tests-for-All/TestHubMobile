package com.example.testhub.testFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.testhub.R
import com.example.testhub.repository.RepositoryNetworkProvider

class TestFragment : Fragment() {


    private var exampleRequestButton: Button? = null

    private val viewModel: ViewModelTests by viewModels {
        ViewModelTestsFactory((requireActivity() as RepositoryNetworkProvider).provideRepository())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tests_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(this.viewLifecycleOwner, this::setState)

        exampleRequestButton = view.findViewById<Button?>(R.id.example_b).apply {
            setOnClickListener {
                sendRequest()
            }
        }
    }

    private fun setState(state: ViewModelTests.State){
        when(state){
            is ViewModelTests.State.Success -> {
                Toast.makeText(requireActivity(), "Success!!!", Toast.LENGTH_SHORT).show()
            }
            is ViewModelTests.State.Error -> {
                Toast.makeText(requireActivity(), "Fail!!!", Toast.LENGTH_SHORT).show()
            }
            else -> Toast.makeText(requireActivity(), "Def", Toast.LENGTH_SHORT).show()
        }
    }
    private fun sendRequest() {
        viewModel.exampleRequest()
    }

    companion object {
        fun newInstance() = TestFragment()
    }
}