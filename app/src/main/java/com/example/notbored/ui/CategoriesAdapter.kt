package com.example.notbored.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notbored.R


class CategoriesAdapter(private val activities: List<String>) :
    RecyclerView.Adapter<CategoriesViewHolder>() {

    private lateinit var listener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoriesViewHolder(
            layoutInflater.inflate(R.layout.activity_item_category, parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val activityPosition = activities[position]
        holder.bind(activityPosition)
    }

    override fun getItemCount() = activities.size
}