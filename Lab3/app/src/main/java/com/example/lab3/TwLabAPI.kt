package com.example.lab3

class TwLabAPI(serverAddress: String = "http://192.168.43.178:8080", var userID: Int = 0) {
    class AuthObj(s: String) {
        val login = "$s/auth/login"
        val signup = "$s/auth/signup"
    }

    class TweetObj(s: String) {
        val new = "$s/tweet/new"
        val delete = "$s/tweet/delete"
        val edit = "$s/tweet/edit"
    }

    class TweetsObj(s: String) {
        val all = "$s/tweets/all"
    }

    class DataObj(s: String, id: Int) {
        val photo = "$s/data/$id/profilephoto"
    }

    val auth = AuthObj(serverAddress)
    val tweet = TweetObj(serverAddress)
    val tweets = TweetsObj(serverAddress)
    val data = DataObj(serverAddress,userID)
}