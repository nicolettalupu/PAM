package com.example.lab3Api.payload

data class NewTweetPayload(
    var id: Long,
    var access_token: String,
    var tweet_text: String,
    var success: Boolean = false,
    var message: String = "",
    var tweet_id: Long = -1,
    var tokenStatus: String = ""
)
