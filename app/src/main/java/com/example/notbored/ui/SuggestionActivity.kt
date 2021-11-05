package com.example.notbored.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuggestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val category = intent.getStringExtra("category")

        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        val participants = prefs.getInt(key, DEFUALT_PARTICIPANTS)

        // Draw category name
        binding.tvSuggestion.text = category

        binding.btnTryAnother.setOnClickListener {
            Log.i("SUGGESTION ACTIVITY", "Try Another button was pressed")
            request(category, participants)
        }

        if (category.equals("random")) binding.lyCategory.isInvisible = false

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Application is loading, please wait")

        request(category, participants)

        binding.btnTryAnother.setOnClickListener {
            request(category, participants)
        }

        if (category.equals("Random")) binding.lyCategory.isInvisible = false

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
                progressDialog.show()
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
                    binding.tvTittle.text = response.activity
                    binding.tvParticipants.text = response.participants.toString()
                    binding.tvCategory.text = response.type

                    when (price) {
                        0 -> binding.tvPrice.text = FREE
                        in 1..3 -> binding.tvPrice.text = LOW
                        in 4..6 -> binding.tvPrice.text = MEDIUM
                        in 7..10 -> binding.tvPrice.text = HIGH
                    }
                }
                progressDialog.dismiss()
            }
        }
    }

    private fun getBoredRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://www.boredapi.com/api/activity/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}