package com.example.testhub.addTestFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.Tag
import com.example.testhub.model.Test


class RecycleTagsAdapter(private val onClickTag: (item: Tag) -> Unit)
    : ListAdapter<Tag, RecycleTagsAdapter.ViewHolderTag>(DiffCallback()) {

    class ViewHolderTag(itemView: View, private val adapter: RecycleTagsAdapter) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.tag_name)
        fun bind(item: Tag, onClickTag: (item: Tag) -> Unit) {
            title.text = item.name
            itemView.setOnClickListener{
                val newList = adapter.currentList.toMutableList()
                newList.removeAt(adapterPosition)
                adapter.submitList(newList)
                onClickTag(item)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Tag>() {
        override fun areItemsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tag, newItem: Tag): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTag {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderTag(inflater.inflate(R.layout.tag_item, parent, false), this)
    }

    override fun onBindViewHolder(holder: ViewHolderTag, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClickTag)
    }
}