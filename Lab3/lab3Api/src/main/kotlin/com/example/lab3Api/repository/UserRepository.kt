package com.example.lab3Api.repository

import com.example.lab3Api.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByAccessTokenAndId(token: String, id: Long): User?
}