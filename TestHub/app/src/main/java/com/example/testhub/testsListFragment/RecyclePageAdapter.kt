package com.example.testhub.testsListFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testhub.R
import com.example.testhub.model.Test

class RecyclePageAdapter(private val onClickPage: (item: Page) -> Unit): ListAdapter<Page, RecyclePageAdapter.ViewHolderPage>(DiffCallback())
{
    class ViewHolderPage(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val page = itemView.findViewById<TextView>(R.id.num_page)
        fun bind(item: Page, adapter: RecyclePageAdapter) {
            page.text = item.page.toString()
            itemView.setOnClickListener{
                adapter.onClickPage(item)
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolderPage {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolderPage(inflater.inflate(R.layout.page_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderPage, position: Int) {
        val item = getItem(position)
        holder.bind(item, this)
    }

    class DiffCallback : DiffUtil.ItemCallback<Page>() {
        override fun areItemsTheSame(oldItem: Page, newItem: Page): Boolean {
            return oldItem.page == newItem.page
        }

        override fun areContentsTheSame(oldItem: Page, newItem: Page): Boolean {
            return oldItem == newItem
        }
    }
}

data class Page (
    val page: Long
)
