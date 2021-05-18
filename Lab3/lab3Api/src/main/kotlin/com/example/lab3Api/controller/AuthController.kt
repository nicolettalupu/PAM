package com.example.lab3Api.controller

import com.example.lab3Api.model.User
import com.example.lab3Api.payload.LoginPayload
import com.example.lab3Api.payload.SignupPayload
import com.example.lab3Api.service.UserService
import org.springframework.web.bind.annotation.*

@RestController()
class AuthController(private val userService: UserService) {

    @PostMapping("/auth/login")
    fun login(@RequestBody loginPayload: LoginPayload): LoginPayload {
        val user = userService.findByUsername(loginPayload.username)

        if (user != null) {
            if (user.password == loginPayload.password) {
                loginPayload.id = user.id
                loginPayload.name = user.name
                loginPayload.access_token = userService.updateAccessTokenByUsername(user)
                loginPayload.success = true
            } else {
                loginPayload.message = "Password is incorrect."
            }
        } else {
            loginPayload.message = "That user doesn't exist."
        }

        return loginPayload
    }

    @PostMapping("/auth/signup")
    fun signup(@RequestBody signupPayload: SignupPayload): SignupPayload {
        if (userService.findByUsername(signupPayload.username) == null) {
            val user = userService.create(signupPayload)
            signupPayload.id = user.id
            signupPayload.access_token = user.accessToken
            signupPayload.success = true
        } else {
            signupPayload.message = "That username is taken."
        }

        return signupPayload
    }

    @GetMapping("/data/{id}/profilephoto")
    fun profilePhoto(@PathVariable(value = "id") id: Long) {

    }
}