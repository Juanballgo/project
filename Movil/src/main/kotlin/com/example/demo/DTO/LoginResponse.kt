package com.example.demo.DTO

data class LoginResponse(
    val token: String,
    val user: UserResponse
)

data class UserResponse(
    val id: Int,
    val name: String,
    val email: String,
    val role: String,
    val phone: String?,
    val address: String?
)