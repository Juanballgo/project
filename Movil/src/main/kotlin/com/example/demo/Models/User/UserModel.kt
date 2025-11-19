package com.example.demo.Models.User

import java.time.LocalDateTime

data class UserModel(
    val id: Int? = null,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val role: String? = null,
    val created_at: LocalDateTime? = null,
    val updated_at: LocalDateTime? = null
)