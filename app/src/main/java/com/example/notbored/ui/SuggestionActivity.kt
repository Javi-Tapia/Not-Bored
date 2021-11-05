package com.example.notbored.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isInvisible
import com.example.notbored.utils.*
import com.example.notbored.data.APIService
import com.example.notbored.data.Response
import com.example.notbored.databinding.ActivitySuggestionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuggestionBinding
    private lateinit var tvTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvParticipants: TextView
    private lateinit var tvCategory: TextView
    private lateinit var tvSuggestion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        tvTitle = binding.tvTitle
        tvPrice = binding.tvPrice
        tvParticipants = binding.tvParticipants
        tvCategory = binding.tvCategory
        tvSuggestion = binding.tvSuggestion

        // Get bundle of intent from CategoriesActivity
        val bundle: Bundle? = intent.extras
        val category = bundle?.getString("category")
        val participants = bundle?.getInt("participants") ?: DEFUALT_PARTICIPANTS

        // Draw category name
        tvSuggestion.text = category

        val btnTryAnother: Button = binding.btnTryAnother
        val one: LinearLayout = binding.lyCategory

        btnTryAnother.setOnClickListener {
            Log.i("SUGGESTION ACTIVITY", "Try Another button was pressed")
            request(category, participants)
        }

        if (category.equals("random")) one.isInvisible = false

        request(category, participants)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun request(query: String?, participants: Int): Boolean {
        query?.let {
            Log.i("SUGGESTION ACTIVITY", it)
            if (it.isNotEmpty()) {
                searchActivity(query.lowercase(), participants)
            }
        }
        return true
    }

    private fun searchActivity(query: String, participants: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call =
                if (query == "random") {
                    getBoredRetrofit().create(APIService::class.java)
                        .getActivities("?participants=$participants")
                } else {
                    getBoredRetrofit().create(APIService::class.java)
                        .getActivities("?participants=$participants&type=$query")
                }

            val response: Response? = call.body()

            runOnUiThread {
                if (call.isSuccessful) {
                    val price: Int = response?.price?.times(10)!!.toInt()
                    tvTitle.text = response.activity
                    tvParticipants.text = response.participants.toString()
                    tvCategory.text = response.type

                    when (price) {
                        0 -> tvPrice.text = FREE
                        in 1..3 -> tvPrice.text = LOW
                        in 4..6 -> tvPrice.text = MEDIUM
                        in 7..10 -> tvPrice.text = HIGH
                    }
                }
            }
        }
    }

    private fun getBoredRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://www.boredapi.com/api/activity/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}