package com.example.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notbored.databinding.ActivityCategoriesBinding
import com.example.notbored.databinding.ActivityMainBinding

class Categories : AppCompatActivity() {


    private lateinit var adapter: AdapterActivities
    private lateinit var binding: ActivityCategoriesBinding

    val itemsActivities = listOf<String>("Education", "Recreational", "Social", "DIY", "Charity"
        ,"Cooking", "Relaxation", "Music", "Busywork")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btn_random: ImageButton = findViewById(R.id.btn_random)

        btn_random.setOnClickListener {

            val intent = Intent(this@Categories, Activities::class.java)
            intent.putExtra("category", "random")
            intent.putExtra("participants", 2)
            startActivity(intent)

        }

        StartRecycleView()


        binding.rvItemActivities.layoutManager = LinearLayoutManager(this)

    }

    private fun StartRecycleView() {


        adapter = AdapterActivities(itemsActivities)
        binding.rvItemActivities.adapter = adapter
        adapter.setOnItemClickListener(object : AdapterActivities.onItemClickListener{

            override fun onItemClick(position: Int) {

                val intent = Intent(this@Categories, Activities::class.java)
                intent.putExtra("category", itemsActivities[position])
                intent.putExtra("participants", 2)
                startActivity(intent)

            }


        })



    }

}