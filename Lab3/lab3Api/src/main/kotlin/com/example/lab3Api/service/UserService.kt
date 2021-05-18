package com.example.lab3Api.service

import com.example.lab3Api.model.User
import com.example.lab3Api.payload.SignupPayload
import com.example.lab3Api.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {
    fun findByUsername(username: String): User? {
        return userRepository.findByUsername(username)
    }

    fun create(signupPayload: SignupPayload): User {
        val user = User()
        user.name = signupPayload.name
        user.username = signupPayload.username
        user.password = signupPayload.password
        user.accessToken = UUID.randomUUID().toString()

        return userRepository.save(user)
    }

    fun updateAccessTokenByUsername(user: User): String {
        user.accessToken = UUID.randomUUID().toString()

        userRepository.save(user)
        return user.accessToken
    }

    fun findByAccessTokenAndId(token: String, id: Long): User? {
        return userRepository.findByAccessTokenAndId(token, id)
    }

    fun findById(id: Long): Optional<User> {
        return userRepository.findById(id)
    }
}