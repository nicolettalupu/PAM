package com.example.lab3Api.payload

import com.example.lab3Api.model.Tweet
import com.google.gson.JsonArray

data class AllTweetsPayload(
    var id: Long,
    var access_token: String,
    var success: Boolean = false,
    var tweets: List<Tweet>?,
    var message: String = "",
    var tokenStatus: String = ""
)