package com.example.testhub.testingFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.UserAnswer

class RecycleTestingAnswersAdapter (private val onClickAnswer: (item: UserAnswer) -> Unit)
    : ListAdapter<UserAnswer, RecycleTestingAnswersAdapter.ViewHolderTestingAnswers>(DiffCallback()) {
    class ViewHolderTestingAnswers(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val answer = itemView.findViewById<TextView>(R.id.answer_text)
        fun bind(item: UserAnswer, adapter: RecycleTestingAnswersAdapter) {
            answer.text = item.text
            setBG(item)
            itemView.setOnClickListener {
                adapter.onClickAnswer(item)
                setBG(item)
            }
        }

        private fun setBG(item: UserAnswer) {
            if (item.isTrue) {
                val drawable = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.rounded_shape_yellow_with_stroke
                )
                itemView.background = drawable
            } else {
                val drawable = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.rounded_shape_white_with_stroke
                )
                itemView.background = drawable
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<UserAnswer>() {
        override fun areItemsTheSame(oldItem: UserAnswer, newItem: UserAnswer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserAnswer, newItem: UserAnswer): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTestingAnswers {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderTestingAnswers(inflater.inflate(R.layout.answer_item_test, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderTestingAnswers, position: Int) {
        val item = getItem(position)
        holder.bind(item, this)
    }
}