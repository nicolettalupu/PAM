package com.example.lab3Api.payload

data class SignupPayload(
    var id: Long = -1,
    var name: String,
    var username: String,
    var password: String,
    var success: Boolean = false,
    var message: String = "",
    var access_token: String = ""
)