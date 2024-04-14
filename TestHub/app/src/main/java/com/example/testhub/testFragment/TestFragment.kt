package com.example.testhub.testFragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.registration.RegistrationFragment
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.response.TestInfo
import kotlinx.coroutines.launch

class TestFragment : Fragment() {

    private var listener: TestFragmentInterface? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is TestFragmentInterface)
            listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.test_list).apply {
            this.layoutManager = GridLayoutManager(requireActivity(), 1)
            val adapter = RecycleTestsAdapter{ test ->
                viewModel.testInfo.observe(this@TestFragment.viewLifecycleOwner, this@TestFragment::showInfo)
                viewModel.loadTestInfo(test.id)
            }
            this.adapter = adapter
            loadTests(adapter)
        }

        view.findViewById<View>(R.id.add_test_b).setOnClickListener {
            listener?.openAddTestLayout()
        }

        view.findViewById<View>(R.id.refresh_test_b).setOnClickListener {
           viewModel.loadTests()
        }
    }

    private fun showInfo(test: TestInfo?){
        Toast.makeText(requireActivity(), test?.toString().orEmpty(), Toast.LENGTH_SHORT).show()
    }
    private fun loadTests(adapter: RecycleTestsAdapter) {
        lifecycleScope.launch{
            viewModel.testsList.collect{listTest ->
                adapter.submitList(listTest)
            }
        }
    }

    companion object {
        interface TestFragmentInterface{
            fun openAddTestLayout()
        }
    }
}