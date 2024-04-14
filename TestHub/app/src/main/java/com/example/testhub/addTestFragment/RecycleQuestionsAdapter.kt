package com.example.testhub.addTestFragment

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.Question

class RecycleQuestionsAdapter: ListAdapter<Question, RecycleQuestionsAdapter.ViewHolderQuestion>(DiffCallback()) {
    class ViewHolderQuestion(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val questionName = itemView.findViewById<TextView>(R.id.question_name)
        fun bind(item: Question) {
            questionName.text = item.name
            itemView.setOnClickListener{
                Log.d("showQuestionInfo", item.toString())
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem.name == newItem.name
                    && oldItem.details == newItem.details
                    && oldItem.answerCreateDtos == newItem.answerCreateDtos
        }

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderQuestion {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderQuestion(inflater.inflate(R.layout.question_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderQuestion, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}