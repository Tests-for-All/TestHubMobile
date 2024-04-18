package com.example.testhub.testingFragment

import android.content.Context
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
import com.example.testhub.model.Question
import com.example.testhub.model.QuestionHidden
import com.example.testhub.model.QuestionResultCreateDto
import com.example.testhub.model.TestToCheck
import com.example.testhub.model.UserAnswer
import com.example.testhub.repository.RepositoryNetworkProvider
import com.example.testhub.retrofit.response.ResultTest
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
    private var questions = mutableListOf<Question>()

    private var listener : TestingInterface? = null

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is TestingInterface)
            listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        testId = arguments?.getSerializable(TEST_ID) as? Long ?: return

        viewModel.testInfo.observe(this.viewLifecycleOwner, this::showTest)
        viewModel.loadTestInfo(testId)
        viewModel.question.observe(this.viewLifecycleOwner, this::showQuestion)
        viewModel.checkTest.observe(this.viewLifecycleOwner, this::showResults)

        titleTest = view.findViewById(R.id.question_title)
        questionText = view.findViewById(R.id.test_question)

        passTestButton = view.findViewById(R.id.pass_test_b)
        passTestButton.visibility = View.GONE
        nextQuestion = view.findViewById(R.id.next_question)
        prevQuestion = view.findViewById(R.id.previous_question)

        view.findViewById<RecyclerView>(R.id.answers_list).apply {
            this.layoutManager = GridLayoutManager(requireActivity(), 1)
            val adapter = RecycleTestingAnswersAdapter(this@TestingFragment::selectAnswer)
            this.adapter = adapter
            answers = adapter
        }

        initButtons()
    }

    private fun showResults(resultTest: ResultTest?) {
        if(resultTest == null)
            Toast.makeText(requireContext(), "Возникла непредвиденная ошибка", Toast.LENGTH_LONG).show()
        else{
            val rez = (resultTest.testResultInformationListDto.percentageCorrectAnswers * 100).toInt()
            Toast.makeText(requireContext(), "Ваш результат: $rez баллов из 100", Toast.LENGTH_LONG).show()
            listener?.openTestLayout()
        }
    }

    private fun selectAnswer(answerToCheck: UserAnswer) {
        answerToCheck.isTrue = !answerToCheck.isTrue
    }

    private fun initButtons() {
        passTestButton.setOnClickListener {
            val questionResult = questions.map {
                QuestionResultCreateDto(it.id, it.answerDtos.filter { it.isTrue==true }.map { it.id }) }
            val test = TestToCheck(
                testId,
                questionResult
            )
            viewModel.checkTest(test)
        }

        nextQuestion.setOnClickListener{
            if(currentQuestion < currentTest.questionListDtos.size - 1){
                currentQuestion++
                viewModel.loadQuestion(currentTest.questionListDtos[currentQuestion].id)
            }
            if(currentQuestion == currentTest.questionListDtos.size - 1){
                passTestButton.visibility = View.VISIBLE
            }
        }

        prevQuestion.setOnClickListener {
            if(currentQuestion > 0){
                currentQuestion--
                viewModel.loadQuestion(currentTest.questionListDtos[currentQuestion].id)
            }
        }
    }

    private fun showQuestion(question: QuestionHidden?) {
        if(question==null)
            return
        val userAnswers = question.answerHiddenDtos.map{
                answer -> UserAnswer(answer.id, answer.text, false)
        }

        if(!questions.any { it.id == question.id })
            questions.add(
                Question(question.id, question.name, question.details, userAnswers)
            )

        answers.submitList(questions[currentQuestion].answerDtos)
        questionText.text = question.details
        titleTest.text = question.name
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

        interface TestingInterface{
            fun openTestLayout()
        }
    }
}