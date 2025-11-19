package com.example.demo.Controllers

import com.example.demo.DTO.LoginResponse
import com.example.demo.DTO.UserResponse
import com.example.demo.Models.User.UserModel
import com.example.demo.Services.JwtService
import com.example.demo.Services.User.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var jwtService: JwtService

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: Map<String, String>): ResponseEntity<Any> {
        val email = loginRequest["email"]
        val password = loginRequest["password"]

        if (email.isNullOrBlank() || password.isNullOrBlank()) {
            return ResponseEntity.badRequest()
                .body(mapOf("error" to "Email y contraseña son requeridos"))
        }

        val user = userService.verifyLogin(email, password)
        return if (user != null && user.id != null) {
            // Generar token JWT
            val token = jwtService.generateToken(
                userId = user.id,
                email = user.email ?: "",
                role = user.role ?: "cliente"
            )

            val loginResponse = LoginResponse(
                token = token,
                user = UserResponse(
                    id = user.id,
                    name = user.name ?: "",
                    email = user.email ?: "",
                    role = user.role ?: "cliente",
                    phone = user.phone,
                    address = user.address
                )
            )
            ResponseEntity.ok(loginResponse)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Credenciales inválidas"))
        }
    }

    @GetMapping("/users")
    fun users(@RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        val cleanToken = token.replace("Bearer ", "")
        return if (jwtService.validateToken(cleanToken)) {
            val users = userService.list1()
            ResponseEntity.ok(users)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Token inválido"))
        }
    }

    @GetMapping("/users/{id}")
    fun search(@PathVariable id: Int, @RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        val cleanToken = token.replace("Bearer ", "")
        return if (jwtService.validateToken(cleanToken)) {
            val user = userService.list3(id)
            if (user != null) {
                ResponseEntity.ok(user)
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf("error" to "Usuario no encontrado"))
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Token inválido"))
        }
    }

    @PostMapping("/users")
    fun save(@RequestBody user: UserModel): ResponseEntity<Any> {
        return try {
            val savedUser = userService.list2(user)
            ResponseEntity.ok(savedUser)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Error interno del servidor"))
        }
    }

    @PutMapping("/users/{id}")
    fun update(@PathVariable id: Int, @RequestBody user: UserModel, @RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        val cleanToken = token.replace("Bearer ", "")
        return if (jwtService.validateToken(cleanToken)) {
            val updatedUser = userService.list4(id, user)
            if (updatedUser != null) {
                ResponseEntity.ok(updatedUser)
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf("error" to "Usuario no encontrado"))
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Token inválido"))
        }
    }

    @DeleteMapping("/users/{id}")
    fun delete(@PathVariable id: Int, @RequestHeader("Authorization") token: String): ResponseEntity<Any> {
        val cleanToken = token.replace("Bearer ", "")
        return if (jwtService.validateToken(cleanToken)) {
            val success = userService.list5(id)
            if (success) {
                ResponseEntity.ok(mapOf("message" to "Usuario eliminado"))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf("error" to "Usuario no encontrado"))
            }
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(mapOf("error" to "Token inválido"))
        }
    }

    // Endpoint para cambiar role (solo admin) - CORREGIDO
    @PutMapping("/admin/users/{userId}/role")
    fun changeUserRole(
        @PathVariable userId: Int,
        @RequestBody roleRequest: Map<String, String>,
        @RequestHeader("Authorization") token: String  // Usar JWT en lugar de Admin-ID
    ): ResponseEntity<Any> {
        val cleanToken = token.replace("Bearer ", "")
        return try {
            // Validar token y que el usuario sea admin
            if (!jwtService.validateToken(cleanToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(mapOf("error" to "Token inválido"))
            }

            val userRole = jwtService.getRoleFromToken(cleanToken)
            if (userRole != "admin") {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(mapOf("error" to "No tienes permisos de administrador"))
            }

            val newRole = roleRequest["role"]
            if (newRole == null) {
                return ResponseEntity.badRequest().body(mapOf("error" to "Role es requerido"))
            }

            // Obtener el adminId del token
            val adminId = jwtService.getUserIdFromToken(cleanToken)

            val success = userService.changeUserRole(userId, newRole, adminId)
            if (success) {
                ResponseEntity.ok(mapOf("message" to "Role actualizado correctamente"))
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(mapOf("error" to "Usuario no encontrado"))
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Error interno del servidor"))
        }
    }
}