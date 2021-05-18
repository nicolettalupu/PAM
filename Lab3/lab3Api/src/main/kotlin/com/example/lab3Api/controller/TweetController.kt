package com.example.lab3Api.controller

import com.example.lab3Api.payload.AllTweetsPayload
import com.example.lab3Api.payload.DeleteTweetPayload
import com.example.lab3Api.payload.EditTweetPayload
import com.example.lab3Api.payload.NewTweetPayload
import com.example.lab3Api.service.TweetService
import com.example.lab3Api.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
class TweetController(private var userService: UserService, private var tweetService: TweetService) {
    @GetMapping("/test")
    fun teset(): AllTweetsPayload {
        return AllTweetsPayload(1, "!234", false, tweetService.getAll().toList(), "", "")
    }

    @PostMapping("/tweet/new")
    fun new(@RequestBody newTweetPayload: NewTweetPayload): NewTweetPayload {
        val user = userService.findByAccessTokenAndId(newTweetPayload.access_token, newTweetPayload.id)
        if (user != null) {
            val time = System.currentTimeMillis()
            newTweetPayload.tweet_id = tweetService.create(newTweetPayload, time).id
            newTweetPayload.success = true
        } else {
            newTweetPayload.message = "Invalid token"
            newTweetPayload.tokenStatus = "invalid"
        }

        return newTweetPayload
    }

    @DeleteMapping("/tweet/delete")
    fun delete(@RequestBody deleteTweetPayload: DeleteTweetPayload): DeleteTweetPayload {
        val user = userService.findByAccessTokenAndId(deleteTweetPayload.access_token, deleteTweetPayload.id)
        if (user != null) {
           val tweet = tweetService.findByIdAndTweetBy(deleteTweetPayload.tweet_id, user.id)
            if (tweet != null) {
                tweetService.delete(tweet)
                deleteTweetPayload.success = true
                deleteTweetPayload.message = "Tweet deleted"
            } else {
                deleteTweetPayload.message = "That's not your tweet buddy. Can't fool me"
            }
        } else {
            deleteTweetPayload.message = "Invalid token"
            deleteTweetPayload.tokenStatus = "invalid"
        }

        return deleteTweetPayload
    }

    @PutMapping("/tweet/edit")
    fun edit(@RequestBody editTweetPayload: EditTweetPayload): EditTweetPayload {
        val user = userService.findByAccessTokenAndId(editTweetPayload.access_token, editTweetPayload.id)
        if (user != null) {
            val tweet = tweetService.findByIdAndTweetBy(editTweetPayload.tweet_id, user.id)
            if (tweet != null) {
                if (System.currentTimeMillis() >= (tweet.tweetTime + 60*1000) && !tweet.edited) {
                    tweet.tweetText = editTweetPayload.tweet_text
                    tweet.edited = true
                    tweetService.update(tweet)

                    editTweetPayload.message = "Tweet edited successfully!"
                    editTweetPayload.success = true
                } else {
                    editTweetPayload.message = "Tweet can't be edited"
                }
            } else {
                editTweetPayload.message = "That's not your tweet buddy. Can't fool me"
            }
        } else {
            editTweetPayload.message = "Invalid token"
            editTweetPayload.tokenStatus = "invalid"
        }

        return editTweetPayload
    }

    @PostMapping("/tweets/all")
    fun allTweets(@RequestBody allTweetsPayload: AllTweetsPayload): AllTweetsPayload {
        val user = userService.findByAccessTokenAndId(allTweetsPayload.access_token, allTweetsPayload.id)
        if (user != null) {
            val tweets = tweetService.getAll().toList()
            if (tweets.isNotEmpty()) {
                allTweetsPayload.success = true
                tweets.forEach {
                    val itUser = userService.findById(it.tweetBy).get()
                    it.name = itUser.name
                    it.username = itUser.username
                }
                allTweetsPayload.tweets = tweets
            } else {
                allTweetsPayload.message = "No tweets available yet"
            }
        } else {
            allTweetsPayload.message = "Invalid Token"
            allTweetsPayload.tokenStatus = "invalid"
        }

        return allTweetsPayload
    }
}
