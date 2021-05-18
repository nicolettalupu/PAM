package com.example.lab3Api.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity(name = "tweets")
data class Tweet(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1,
    var tweetBy: Long,
    var tweetText: String = "",
    var tweetTime: Long,
    var edited: Boolean = false
) {
    var name: String = ""
    var username: String = ""
}