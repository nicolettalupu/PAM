package com.example.lab3Api.service

import com.example.lab3Api.model.Tweet
import com.example.lab3Api.payload.EditTweetPayload
import com.example.lab3Api.payload.NewTweetPayload
import com.example.lab3Api.repository.TweetRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class TweetService(private var tweetRepository: TweetRepository) {
    fun create(newTweetPayload: NewTweetPayload, time: Long): Tweet {
        return tweetRepository.save(Tweet(tweetBy = newTweetPayload.id, edited = false, tweetText = newTweetPayload.tweet_text, tweetTime = time))
    }

    fun findByIdAndTweetBy(id: Long, tweetBy: Long): Tweet? {
        return tweetRepository.findByIdAndTweetBy(id, tweetBy)
    }

    fun delete(tweet: Tweet) {
        tweetRepository.delete(tweet)
    }

    fun update(tweet: Tweet) {
        tweetRepository.save(tweet)
    }

    fun getAll(): MutableIterable<Tweet> {
        return tweetRepository.findAll()
    }
}