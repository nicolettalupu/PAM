package com.example.lab3Api.payload

data class EditTweetPayload(
    var id: Long,
    var access_token: String,
    var tweet_id: Long,
    var tweet_text: String,
    var success: Boolean = false,
    var message: String = "",
    var tokenStatus: String = ""
)