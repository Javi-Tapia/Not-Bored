package com.example.notbored.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.databinding.ActivityItemCategoryBinding

class CategoriesViewHolder(view: View, listener: CategoriesAdapter.OnItemClickListener) :
    RecyclerView.ViewHolder(view) {

    private val binding = ActivityItemCategoryBinding.bind(view)

    fun bind(activityPosition: String) {
        binding.nameActivity.text = activityPosition
    }

    init {
        view.setOnClickListener {
            listener.onItemClick(adapterPosition)
        }
    }
}