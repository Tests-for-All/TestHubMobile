package com.example.testhub.addTestFragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testhub.R
import com.example.testhub.model.Answer
import com.example.testhub.model.Question
import com.example.testhub.model.Tag
import com.example.testhub.model.TestToAdd
import com.example.testhub.repository.RepositoryNetworkProvider
import kotlinx.coroutines.launch

class AddTestFragment : Fragment() {

    private var listener: AddTestInterface? = null
    private val tagList =  mutableListOf<Tag>()
    private lateinit var answerRV: RecycleAnswersAdapter
    private lateinit var questionRV: RecycleQuestionsAdapter
    private lateinit var nameTestET: EditText

    private val viewModel: ViewModelAddTest by viewModels {
        ViewModelAddTestFactory((requireActivity() as RepositoryNetworkProvider).provideRepository())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.add_test_fragment, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is AddTestInterface)
            listener = context
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.testStatus.observe(this.viewLifecycleOwner, this::setState)

        nameTestET = view.findViewById(R.id.nameTestEditText)

        view.findViewById<RecyclerView>(R.id.tag_list).apply {
            this.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            val adapter = RecycleTagsAdapter{
                tag -> tagList.add(tag)
            }
            this.adapter = adapter
            loadTags(adapter)
        }

        view.findViewById<RecyclerView>(R.id.question_list).apply{
            this.layoutManager = GridLayoutManager(requireActivity(), 1)
            val adapter = RecycleQuestionsAdapter()
            this.adapter = adapter
            questionRV = adapter
        }

        view.findViewById<Button>(R.id.add_questions_b).setOnClickListener {
            addQuestions()
        }

        view.findViewById<View>(R.id.add_test).setOnClickListener{
            addTest()
            listener?.backToTestLayout()
        }
    }

    private fun setState(state: ViewModelAddTest.State) {
        when(state){
            is ViewModelAddTest.State.Default ->{}
            is ViewModelAddTest.State.Error -> {
                Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            }
            is ViewModelAddTest.State.Success -> {
                Toast.makeText(requireContext(), "Успешно добавлен", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addTest() {
        val test = TestToAdd(
            nameTestET.text.toString(),
            tagList,
            questionRV.currentList
        )
        viewModel.saveTest(test)
    }

    private fun addQuestions() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.add_question, null)
        builder.setView(dialogView)

        dialogView.findViewById<RecyclerView>(R.id.answers_list).apply {
            this.layoutManager = GridLayoutManager(requireActivity(), 1)
            val adapter = RecycleAnswersAdapter()
            this.adapter = adapter
            answerRV = adapter
        }

        val nameQuestionET = dialogView.findViewById<EditText>(R.id.nameQuestionEditText)
        val detailQuestionET = dialogView.findViewById<EditText>(R.id.detailQuestionEditText)
        val countAnswerET = dialogView.findViewById<EditText>(R.id.countAnswerEditText)
        val addQuestion = dialogView.findViewById<Button>(R.id.add_question_b)

        countAnswerET.addTextChangedListener ( object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val count = s.toString().toIntOrNull() ?: 0

                val newList = answerRV.currentList.toMutableList()
                newList.clear()

                repeat(count) {
                    newList.add(Answer(" ", false))
                }

                Log.d("listCount", newList.size.toString())
                answerRV.submitList(newList)
            }
        } )

        addQuestion.setOnClickListener {
            val question = Question(
                nameQuestionET.text.toString(),
                detailQuestionET.text.toString(),
                answerRV.currentList
            )

            val newList = questionRV.currentList.toMutableList()
            newList.add(question)
            questionRV.submitList(newList)

            Toast.makeText(requireContext(), "Вопрос добавлен", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun loadTags(adapter: RecycleTagsAdapter) {
        lifecycleScope.launch{
            viewModel.tagsList.collect{listTest ->
                adapter.submitList(listTest)
            }
        }
    }

    companion object{
        interface AddTestInterface{
            fun backToTestLayout()
        }
    }

}