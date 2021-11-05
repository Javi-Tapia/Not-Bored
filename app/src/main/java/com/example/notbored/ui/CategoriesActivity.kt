package com.example.notbored.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notbored.databinding.ActivityCategoriesBinding
import com.example.notbored.utils.DEFUALT_PARTICIPANTS

class CategoriesActivity : AppCompatActivity() {

    private lateinit var adapter: CategoriesAdapter
    private lateinit var binding: ActivityCategoriesBinding
    private var participants = DEFUALT_PARTICIPANTS

    val itemsActivities = listOf(
        "Education",
        "Recreational",
        "Social",
        "DIY",
        "Charity",
        "Cooking",
        "Relaxation",
        "Music",
        "Busywork"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btnRandom: ImageButton = binding.btnRandom

        // Get extras of intent from MainActivity
        participants = intent.extras?.getString("participants")?.toInt() ?: participants

        btnRandom.setOnClickListener {
            val intent = Intent(this@CategoriesActivity, SuggestionActivity::class.java)
            intent.putExtra("category", "random")
            intent.putExtra("participants", participants)
            startActivity(intent)
        }

        setupRecyclerView()

        binding.rvItemActivities.layoutManager = LinearLayoutManager(this)
    }

    private fun setupRecyclerView() {
        adapter = CategoriesAdapter(itemsActivities)
        binding.rvItemActivities.adapter = adapter
        adapter.setOnItemClickListener(object : CategoriesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@CategoriesActivity, SuggestionActivity::class.java)
//                Log.i("ITEM PRESSED", itemsActivities[position])
                intent.putExtra("category", itemsActivities[position])
                intent.putExtra("participants", participants)
                startActivity(intent)
            }
        })
    }
}