package com.example.notbored

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isInvisible
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Activities : AppCompatActivity() {

    lateinit var tvTittle: TextView
    lateinit var tvPrice: TextView
    lateinit var tvParticipants: TextView
    lateinit var tvCategoty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activities)

        val bundle: Bundle? = intent.extras
        val category = bundle?.getString("category")
        val participants = 1

        val btn_tryAnother: Button = findViewById(R.id.btn_try_another)
        val one: LinearLayout = findViewById(R.id.ly_category)



        btn_tryAnother.setOnClickListener {
            Request(category, participants)
        }

        tvTittle= findViewById(R.id.tv_tittle)
        tvPrice = findViewById(R.id.tv_price)
        tvParticipants = findViewById(R.id.tv_participants)
        tvCategoty = findViewById(R.id.tv_category)

        if (category.equals("random")) one.isInvisible = false

        Request(category, participants)


    }



    fun Request(query:String?, participants: Int): Boolean{
        query?.let {
            if (it.isNotEmpty()){
                searchActivity(query.lowercase(), participants)
            }
        }
        return true
    }

    private fun searchActivity(query: String, participants:Int) {

        CoroutineScope(Dispatchers.IO).launch {

            val llamada =

                if(query.equals("random")){
                    getBoredRetrofit().create(APIService::class.java).getActivities("?participants=$participants")
                }else{
                    getBoredRetrofit().create(APIService::class.java).getActivities("?participants=$participants&type=$query")
                }

            val respuesta: ResponseActivity? = llamada.body()

            runOnUiThread {
                if (llamada.isSuccessful) {

                    val price:Int = respuesta?.price?.times(10)!!.toInt()

                    tvTittle.setText(respuesta?.activity)
                    tvParticipants.setText(respuesta?.participants.toString())
                    tvCategoty.setText(respuesta?.type)



                    when (price) {
                        0 -> tvPrice.setText("Free")
                        in 1..3 -> tvPrice.setText("Low")
                        in 4..6 -> tvPrice.setText("Medium")
                        in 7..10 -> tvPrice.setText("High")
                    }
                }

            }
        }

    }

    private fun getBoredRetrofit(): Retrofit {

        return Retrofit.Builder().baseUrl("https://www.boredapi.com/api/activity/").
        addConverterFactory(GsonConverterFactory.create()).build()

    }


}