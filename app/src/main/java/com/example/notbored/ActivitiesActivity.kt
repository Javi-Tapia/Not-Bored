package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notbored.databinding.ActivityActivitiesBinding
import com.example.notbored.databinding.ActivityTermsAndCondsBinding
import com.example.notbored.viewmodel.ActivitiesViewModel

class
ActivitiesActivity : AppCompatActivity() {

    private val viewModel by viewModels<ActivitiesViewModel>()
    private lateinit var binding: ActivityActivitiesBinding
    private lateinit var adapter: ActivitiesAdapter
    private val categories = listOf("education", "recreational", "social", "diy", "charity", "cooking", "relaxation", "music", "busywork")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setListeners()
    }

    private fun setupRecyclerView() {
        adapter = ActivitiesAdapter(categories)
        binding.rvActivites.layoutManager = LinearLayoutManager(this)
        binding.rvActivites.adapter = adapter
    }

    private fun setListeners() {
        TODO("Not yet implemented")
    }
}