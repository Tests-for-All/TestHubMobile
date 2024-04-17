package com.example.testhub.addTestFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.QuestionAdd

class RecycleQuestionsAdapter: ListAdapter<QuestionAdd, RecycleQuestionsAdapter.ViewHolderQuestion>(DiffCallback()) {
    class ViewHolderQuestion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionName = itemView.findViewById<TextView>(R.id.question_name)
        private val deleteQuestion = itemView.findViewById<View>(R.id.delete_question)
        fun bind(item: QuestionAdd, adapter: RecycleQuestionsAdapter) {
            questionName.text = item.name
            itemView.setOnClickListener{
                Log.d("showQuestionInfo", item.toString())
            }
            deleteQuestion.setOnClickListener {
                val newList = adapter.currentList.toMutableList()
                newList.remove(item)
                adapter.submitList(newList)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<QuestionAdd>() {
        override fun areItemsTheSame(oldItem: QuestionAdd, newItem: QuestionAdd): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.details == newItem.details
                    && oldItem.answerCreateDtos == newItem.answerCreateDtos
        }

        override fun areContentsTheSame(oldItem: QuestionAdd, newItem: QuestionAdd): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderQuestion {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderQuestion(inflater.inflate(R.layout.question_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderQuestion, position: Int) {
        val item = getItem(position)
        holder.bind(item, this)
    }
}