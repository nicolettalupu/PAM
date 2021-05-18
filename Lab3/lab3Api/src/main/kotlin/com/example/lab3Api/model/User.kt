package com.example.lab3Api.model

import javax.persistence.*

@Entity(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = -1,
    var name: String = "",
    var username: String = "",
    var password: String = "",
    var accessToken: String = "",
    var verified: Boolean = false
) {}