package com.example.demo.Services.User

import com.example.demo.Models.User.UserModel
import com.example.demo.Services.PasswordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class UserService {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var passwordService: PasswordService

    fun list1(): List<UserModel> {
        val sql = "SELECT id, name, email, phone, address, created_at, updated_at FROM users"
        return jdbcTemplate.query(sql, RowMapper<UserModel> { rs, _ ->
            UserModel(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                email = rs.getString("email"),
                password = null, // No devolver la contraseña
                phone = rs.getString("phone"),
                address = rs.getString("address"),
                created_at = if (rs.getTimestamp("created_at") != null)
                    rs.getTimestamp("created_at").toLocalDateTime() else null,
                updated_at = if (rs.getTimestamp("updated_at") != null)
                    rs.getTimestamp("updated_at").toLocalDateTime() else null
            )
        })
    }

    fun list2(user: UserModel): UserModel {
        // Validar que tenga contraseña
        if (user.password.isNullOrBlank()) {
            throw IllegalArgumentException("La contraseña es requerida")
        }

        // Encriptar la contraseña
        val encryptedPassword = passwordService.encryptPassword(user.password)

        val sql = """
            INSERT INTO users (name, email, password, phone, address, created_at, updated_at) 
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """
        val now = LocalDateTime.now()

        jdbcTemplate.update(
            sql,
            user.name,
            user.email,
            encryptedPassword,
            user.phone,
            user.address,
            java.sql.Timestamp.valueOf(now),
            java.sql.Timestamp.valueOf(now)
        )

        return user.copy(password = null) // No retornar la contraseña
    }

    fun list3(id: Int): UserModel? {
        val sql = "SELECT id, name, email, phone, address, created_at, updated_at FROM users WHERE id = ?"
        return jdbcTemplate.query(sql, arrayOf(id), RowMapper<UserModel> { rs, _ ->
            UserModel(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                email = rs.getString("email"),
                password = null,
                phone = rs.getString("phone"),
                address = rs.getString("address"),
                created_at = if (rs.getTimestamp("created_at") != null)
                    rs.getTimestamp("created_at").toLocalDateTime() else null,
                updated_at = if (rs.getTimestamp("updated_at") != null)
                    rs.getTimestamp("updated_at").toLocalDateTime() else null
            )
        }).firstOrNull()
    }

    fun list4(id: Int, user: UserModel): UserModel? {
        val updates = mutableListOf<String>()
        val params = mutableListOf<Any>()

        if (user.name != null) {
            updates.add("name = ?")
            params.add(user.name)
        }
        if (user.email != null) {
            updates.add("email = ?")
            params.add(user.email)
        }
        if (user.password != null) {
            // Encriptar la nueva contraseña
            updates.add("password = ?")
            params.add(passwordService.encryptPassword(user.password))
        }
        if (user.phone != null) {
            updates.add("phone = ?")
            params.add(user.phone)
        }
        if (user.address != null) {
            updates.add("address = ?")
            params.add(user.address)
        }

        // ⚠️ NO permitir cambiar el role desde aquí
        // Solo el admin podrá cambiar roles mediante un método específico

        if (updates.isEmpty()) return null

        // Agregar updated_at
        updates.add("updated_at = ?")
        params.add(java.sql.Timestamp.valueOf(LocalDateTime.now()))

        val sql = "UPDATE users SET ${updates.joinToString(", ")} WHERE id = ?"
        params.add(id)

        val filas = jdbcTemplate.update(sql, *params.toTypedArray())
        return if (filas > 0) user.copy(id = id, password = null) else null
    }

    fun list5(id: Int): Boolean {
        val sql = "DELETE FROM users WHERE id = ?"
        val filas = jdbcTemplate.update(sql, id)
        return filas > 0
    }

    // Método para verificar login
    fun verifyLogin(email: String, password: String): UserModel? {
        val sql = "SELECT * FROM users WHERE email = ?"
        val user = jdbcTemplate.query(sql, arrayOf(email), RowMapper<UserModel> { rs, _ ->
            UserModel(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                email = rs.getString("email"),
                password = rs.getString("password"), // Necesitamos la contraseña encriptada para verificar
                phone = rs.getString("phone"),
                address = rs.getString("address"),
                created_at = if (rs.getTimestamp("created_at") != null)
                    rs.getTimestamp("created_at").toLocalDateTime() else null,
                updated_at = if (rs.getTimestamp("updated_at") != null)
                    rs.getTimestamp("updated_at").toLocalDateTime() else null
            )
        }).firstOrNull()

        return if (user != null && user.password != null &&
            passwordService.verifyPassword(password, user.password)) {
            user.copy(password = null) // No retornar la contraseña
        } else {
            null
        }
    }
    fun changeUserRole(userId: Int, newRole: String, adminId: Integer?): Boolean {
        // Validar roles válidos
        val validRoles = listOf("admin", "cliente", "tecnico")

        if (!validRoles.contains(newRole)) {
            throw IllegalArgumentException("Role no válido: $newRole. Roles válidos: admin, cliente, tecnico")
        }

        val sql = "UPDATE users SET role = ?, updated_at = ? WHERE id = ?"
        val filas = jdbcTemplate.update(
            sql,
            newRole,
            Timestamp.valueOf(LocalDateTime.now()),
            userId
        )
        return filas > 0
    }
}