package com.example.demo.Models

import java.util.Date

data class ServiceModel(
    val id: Int? = null,
    val cliente_id: Int? = null,
    val tecnico_id: Int? = null,
    val tipo_equipo: String? = null,
    val marca: String? = null,
    val modelo: String? = null,
    val descripcion_problema: String? = null,
    val estado: String? = null,
    val diagnostico: String? = null,
    val solucion: String? = null,
    val costo: Double? = null,
    val fecha_solicitud: Date? = null,
    val fecha_atendido: Date? = null,
    val fecha_completado: Date? = null
)