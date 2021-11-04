package com.example.notbored.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _inputValid = MutableLiveData<Boolean>()
    val inputValid: LiveData<Boolean>
        get() = _inputValid

    fun inputDataChanged(participants: String) {
        _inputValid.value = participants.toInt() >= 1
    }
}