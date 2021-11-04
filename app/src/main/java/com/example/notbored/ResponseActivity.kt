package com.example.notbored

import com.google.gson.annotations.SerializedName

data class ResponseActivity (var activity: String, var accessibility: Float,var type: String,
                             var participants: Int, var price: Float
                             , var link: String, var key: String) {
}