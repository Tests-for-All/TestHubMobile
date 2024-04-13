package com.example.testhub.testFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.Test

class RecyclerTestsAdapter(private val onClickTest: (item: Test) -> Unit)
    : ListAdapter<Test, RecyclerTestsAdapter.ViewHolderActor>(DiffCallback()) {
    class ViewHolderActor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.test_name)
        private val tags = itemView.findViewById<TextView>(R.id.test_tag)
        fun bind(item: Test, onClickTest: (item: Test) -> Unit) {
            title.text = item.name
            tags.text = "test test test"

            itemView.setOnClickListener{
                onClickTest(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Test>() {
        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderActor {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderActor(inflater.inflate(R.layout.item_test, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderActor, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickTest)
    }
}