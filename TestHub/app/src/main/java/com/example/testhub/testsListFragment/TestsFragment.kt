package com.example.testhub.testsListFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testhub.R
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.response.TestInfo
import kotlinx.coroutines.launch

class TestsFragment : Fragment() {

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
        viewModel.testInfo.observe(this.viewLifecycleOwner, this::showInfo)
        view.findViewById<RecyclerView>(R.id.test_list).apply {
            this.layoutManager = GridLayoutManager(requireActivity(), 1)
            val adapter = RecycleTestsAdapter{ test ->
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
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.test_detail, null)
        builder.setView(dialogView)
        val alertDialog = builder.show()

        val testName = dialogView.findViewById<TextView>(R.id.test_name)
        val testCreator = dialogView.findViewById<TextView>(R.id.test_creator)
        val tagsRV = dialogView.findViewById<RecyclerView>(R.id.tag_list)
        val countQuestions = dialogView.findViewById<TextView>(R.id.count_question)
        val takeTestB = dialogView.findViewById<Button>(R.id.take_test)

        testName.text = test?.name
        testCreator.text = "Создатель: ${test?.user?.username}"
        tagsRV.apply {
            this.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = RecycleTagInfoAdapter()
            this.adapter = adapter
            adapter.submitList(test?.tags)
        }

        countQuestions.text = "Количество вопросов: ${test?.questionListDtos?.size.toString()?.toIntOrNull() ?: 0}"

        takeTestB.setOnClickListener {
            listener?.openTestingFragment(test?.id ?: -1)
            alertDialog.dismiss()
        }
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
            fun openTestingFragment(testId: Long)
        }
    }
}