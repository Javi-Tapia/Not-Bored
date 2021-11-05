package com.example.notbored.data

data class Response(
    var activity: String,
    var accessibility: Float,
    var type: String,
    var participants: Int,
    var price: Float,
    var link: String,
    var key: String
)