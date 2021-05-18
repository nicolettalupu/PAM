package com.example.lab3Api.payload

data class DeleteTweetPayload(
    var id: Long,
    var access_token: String,
    var tweet_id: Long,
    var success: Boolean = false,
    var message: String = "",
    var tokenStatus: String = ""
)