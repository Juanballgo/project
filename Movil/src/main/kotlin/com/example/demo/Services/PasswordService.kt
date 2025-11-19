package com.example.demo.Services

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordService {

    private val passwordEncoder = BCryptPasswordEncoder()

    fun encryptPassword(password: String): String {
        return passwordEncoder.encode(password)
    }

    fun verifyPassword(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }
}