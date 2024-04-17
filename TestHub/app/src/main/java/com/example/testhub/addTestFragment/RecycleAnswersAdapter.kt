package com.example.testhub.addTestFragment

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.Answer

class RecycleAnswersAdapter : ListAdapter<Answer, RecycleAnswersAdapter.ViewHolderAnswer>(DiffCallback()) {
    class ViewHolderAnswer(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val answer = itemView.findViewById<EditText>(R.id.answerEditText)
        private val switch = itemView.findViewById<Switch>(R.id.answer_is_true)
        fun bind(item: Answer){
            answer.setText(item.text)
            switch.isChecked = item.isTrue
            answer.addTextChangedListener ( object: TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    item.text = s.toString()
                    Log.d("checkAnswer", item.toString())
                }
            })

            switch.setOnCheckedChangeListener { _, isChecked ->
                item.isTrue = isChecked
                Log.d("checkAnswer", item.toString())
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Answer>() {
        override fun areItemsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(oldItem: Answer, newItem: Answer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAnswer {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderAnswer(inflater.inflate(R.layout.answer_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderAnswer, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}