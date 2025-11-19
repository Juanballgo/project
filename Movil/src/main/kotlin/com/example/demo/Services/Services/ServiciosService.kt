package com.example.demo.Services

import com.example.demo.Models.ServiceModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDateTime

@Service
class ServiciosService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    // SELECT (traer todos los servicios)
    fun Servislist1(): List<ServiceModel> {
        val sql = "SELECT * FROM servicios"
        return jdbcTemplate.query(sql, RowMapper<ServiceModel> { rs, _ ->
            ServiceModel(
                id = rs.getInt("id"),
                cliente_id = rs.getInt("cliente_id"),
                tecnico_id = rs.getInt("tecnico_id"),
                tipo_equipo = rs.getString("tipo_equipo"),
                marca = rs.getString("marca"),
                modelo = rs.getString("modelo"),
                descripcion_problema = rs.getString("descripcion_problema"),
                estado = rs.getString("estado"),
                diagnostico = rs.getString("diagnostico"),
                solucion = rs.getString("solucion"),
                costo = rs.getDouble("costo"),
                fecha_solicitud = if (rs.getDate("fecha_solicitud") != null) rs.getDate("fecha_solicitud") else null,
                fecha_atendido = if (rs.getDate("fecha_atendido") != null) rs.getDate("fecha_atendido") else null,
                fecha_completado = if (rs.getDate("fecha_completado") != null) rs.getDate("fecha_completado") else null
            )
        })
    }

    // INSERT (crear un servicio) - MODIFICADO: fecha_solicitud automática
    fun Servislist2(servicio: ServiceModel): ServiceModel {
        val sql = """
            INSERT INTO servicios (cliente_id, tecnico_id, tipo_equipo, marca, modelo, descripcion_problema, estado, diagnostico, solucion, costo, fecha_solicitud, fecha_atendido, fecha_completado) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """

        // Usar la fecha actual para fecha_solicitud
        val fechaSolicitudActual = Date(System.currentTimeMillis())

        jdbcTemplate.update(
            sql,
            servicio.cliente_id,
            servicio.tecnico_id,
            servicio.tipo_equipo,
            servicio.marca,
            servicio.modelo,
            servicio.descripcion_problema,
            servicio.estado ?: "Pendiente", // Estado por defecto
            servicio.diagnostico,
            servicio.solucion,
            servicio.costo,
            fechaSolicitudActual,
            if (servicio.fecha_atendido != null) Date(servicio.fecha_atendido.time) else null,
            if (servicio.fecha_completado != null) Date(servicio.fecha_completado.time) else null
        )
        return servicio.copy(fecha_solicitud = fechaSolicitudActual)
    }

    // UPDATE (actualizar un servicio por id) - MODIFICADO: No cambiar fecha_solicitud
    fun Servislist4(id: Int, servicio: ServiceModel): ServiceModel {
        val sql = """
            UPDATE servicios 
            SET cliente_id = ?, tecnico_id = ?, tipo_equipo = ?, marca = ?, modelo = ?, descripcion_problema = ?, estado = ?, diagnostico = ?, solucion = ?, costo = ?, fecha_atendido = ?, fecha_completado = ?
            WHERE id = ?
        """
        jdbcTemplate.update(
            sql,
            servicio.cliente_id,
            servicio.tecnico_id,
            servicio.tipo_equipo,
            servicio.marca,
            servicio.modelo,
            servicio.descripcion_problema,
            servicio.estado,
            servicio.diagnostico,
            servicio.solucion,
            servicio.costo,
            if (servicio.fecha_atendido != null) Date(servicio.fecha_atendido.time) else null,
            if (servicio.fecha_completado != null) Date(servicio.fecha_completado.time) else null,
            id
        )
        return servicio
    }

    // DELETE (eliminar un servicio por id)
    fun Servislist5(id: Int): Boolean {
        val sql = "DELETE FROM servicios WHERE id = ?"
        val filas = jdbcTemplate.update(sql, id)
        return filas > 0
    }

    // GET por ID
    fun Servislist3(id: Int): ServiceModel? {
        val sql = "SELECT * FROM servicios WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id), RowMapper<ServiceModel> { rs, _ ->
            ServiceModel(
                id = rs.getInt("id"),
                cliente_id = rs.getInt("cliente_id"),
                tecnico_id = rs.getInt("tecnico_id"),
                tipo_equipo = rs.getString("tipo_equipo"),
                marca = rs.getString("marca"),
                modelo = rs.getString("modelo"),
                descripcion_problema = rs.getString("descripcion_problema"),
                estado = rs.getString("estado"),
                diagnostico = rs.getString("diagnostico"),
                solucion = rs.getString("solucion"),
                costo = rs.getDouble("costo"),
                fecha_solicitud = if (rs.getDate("fecha_solicitud") != null) rs.getDate("fecha_solicitud") else null,
                fecha_atendido = if (rs.getDate("fecha_atendido") != null) rs.getDate("fecha_atendido") else null,
                fecha_completado = if (rs.getDate("fecha_completado") != null) rs.getDate("fecha_completado") else null
            )
        }).firstOrNull()
    }
}