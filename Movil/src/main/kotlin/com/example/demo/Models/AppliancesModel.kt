package com.example.demo.Models

import java.awt.Image
import java.util.Date

data class AppliancesModel(
    val id: Int? = null,
    val type: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val purchase_date: Date? = Date(),
    val image: Image? = null,
    val user_id: Int? = null,
)