package com.example.notbored.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.example.notbored.databinding.ActivityMainBinding
import com.example.notbored.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val key = "PARTICIPANTS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        participants = if (!binding.etParticipantsInput.text.isNullOrEmpty())
//            binding.etParticipantsInput.text.toString() as Int else participants


        binding.etParticipantsInput.doAfterTextChanged {
            Log.i("PARTICIPANTS", binding.etParticipantsInput.text.toString())
            viewModel.inputDataChanged(binding.etParticipantsInput.text.toString())
        }

        setObservers()

        setListeners()
    }

    private fun setObservers() {
        viewModel.inputValid.observe(this, {
            binding.btnStart.isEnabled = it
        })
    }

    private fun setListeners() {
        binding.btnStart.setOnClickListener {
            if (it.isEnabled) {
                val intent = Intent(this, CategoriesActivity::class.java)
                val participants = binding.etParticipantsInput.text.toString()
                val prefs = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = prefs.edit()
                editor.putInt(key,participants.toInt())
                editor.apply()
                startActivity(intent)
                finish()
            }
        }

        binding.tvTermsAndConditions.setOnClickListener {
            val intent = Intent(this, TermsAndCondsActivity::class.java)
            startActivity(intent)
        }
    }
}