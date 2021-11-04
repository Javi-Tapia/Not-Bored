package com.example.notbored

import ActivityViewHolder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AdapterActivities(private val activities :List<String>):RecyclerView.Adapter<ActivityViewHolder>() {


    private lateinit var mlistener: onItemClickListener


    interface onItemClickListener{

        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener){

        mlistener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val layoutInflater=LayoutInflater.from(parent.context)
        return ActivityViewHolder(layoutInflater.inflate(R.layout.item_activity,parent,false), mlistener)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
       val activityPosition = activities[position]
       holder.bind(activityPosition)

    }

    override fun getItemCount()=activities.size



}