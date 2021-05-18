package com.example.lab3Api.repository

import com.example.lab3Api.model.Tweet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TweetRepository: CrudRepository<Tweet, Long> {
    fun findByIdAndTweetBy(id: Long, tweetBy: Long): Tweet?
}