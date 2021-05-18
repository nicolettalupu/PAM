package com.example.lab3Api.payload

data class LoginPayload(
    var id: Long = -1,
    var name: String = "",
    var username: String,
    var password: String,
    var access_token: String = "",
    var success: Boolean = false,
    var message: String = ""
)