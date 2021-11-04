package com.example.notbored

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModel
import androidx.fragment.app.viewModels
import com.example.notbored.databinding.ActivityMainBinding
import com.example.notbored.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.etParticipantsInput.doAfterTextChanged {
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
                val intent = Intent(this, Categories::class.java).apply {
                    putExtra("participants", binding.etParticipantsInput.text.toString())
                }
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