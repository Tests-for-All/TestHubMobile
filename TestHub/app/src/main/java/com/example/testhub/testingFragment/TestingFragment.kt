package com.example.testhub.testingFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.QuestionGet
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.response.TestInfo

class TestingFragment : Fragment() {

    private lateinit var titleTest : TextView
    private lateinit var questionText : TextView
    private lateinit var answers: RecycleTestingAnswersAdapter
    private lateinit var passTestButton : Button
    private lateinit var nextQuestion : View
    private lateinit var prevQuestion : View

    private var testId: Long = 0
    private var currentQuestion = 0
    private lateinit var currentTest : TestInfo

    private val viewModel: ViewModelTesting by viewModels {
        ViewModelTestingFactory((requireActivity() as RepositoryNetworkProvider).provideRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.test_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testId = arguments?.getSerializable(TEST_ID) as? Long ?: return

        viewModel.testInfo.observe(this.viewLifecycleOwner, this::showTest)
        viewModel.loadTestInfo(testId)
        viewModel.question.observe(this.viewLifecycleOwner, this::showQuestion)

        titleTest = view.findViewById(R.id.question_title)
        questionText = view.findViewById(R.id.test_question)

        passTestButton = view.findViewById(R.id.pass_test_b)
        nextQuestion = view.findViewById(R.id.next_question)
        prevQuestion = view.findViewById(R.id.previous_question)

        view.findViewById<RecyclerView>(R.id.answers_list).apply {
            this.layoutManager = GridLayoutManager(requireActivity(), 1)
            val adapter = RecycleTestingAnswersAdapter()
            this.adapter = adapter
            answers = adapter
        }

        initButtons()
    }

    private fun initButtons() {
        passTestButton.setOnClickListener {

        }

        nextQuestion.setOnClickListener{
            if(currentQuestion < currentTest.questionListDtos.size - 1){
                currentQuestion++
                viewModel.loadQuestion(currentTest.questionListDtos[currentQuestion].id)
            }
        }

        prevQuestion.setOnClickListener {
            if(currentQuestion > 0){
                currentQuestion--
                viewModel.loadQuestion(currentTest.questionListDtos[currentQuestion].id)
            }
        }
    }

    private fun showQuestion(question: QuestionGet?) {
        answers.submitList(question?.answerDtos)
        questionText.text = question?.details
        titleTest.text = question?.name
    }

    private fun showTest(testInfo: TestInfo?) {
        currentTest = testInfo!!
        if(currentQuestion < currentTest.questionListDtos.size)
            viewModel.loadQuestion(currentTest.questionListDtos[currentQuestion].id)

    }

    companion object {
        fun create(testId: Long) = TestingFragment().also {
            val args = bundleOf(
                TEST_ID to testId
            )
            it.arguments = args
        }
        private const val TEST_ID = "test_id"
    }
}