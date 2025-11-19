package com.example.demo.Services

import com.example.demo.Models.AppliancesModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Service

@Service
class AppliancesService {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    fun list1(): List<String> {
        val sql = "select * from appliances"
        return jdbcTemplate.query(sql, RowMapper<String> { rs, _ ->
            val type = rs.getString("type")
            val brand = rs.getString("brand")
            "Tipo de electrodomestico: $type Marca: $brand "
        })
    }
    fun list2(appliance: AppliancesModel): AppliancesModel {
        val sql = "INSERT INTO appliances(type, brand, model,user_id,purchase_date) VALUES (?, ?, ?,?,?)"
        jdbcTemplate.update(sql, appliance.type, appliance.brand, appliance.model, appliance.user_id,appliance.purchase_date )
        return appliance
    }
    fun list3(id: Int): List<String> {
        val sql1 = "SELECT * from appliances WHERE id = ?"
        return jdbcTemplate.query(sql1, arrayOf(id), RowMapper<String> { rs, _ ->
            val detalles =
               "id electrodomestico: "+ rs.getString("id") + " " + " id de usuario asignado: " + rs.getString("user_id")+ " " +" Tipo de electrodomestico: " +rs.getString("type")
            "$detalles"
        })
    }
    fun list4(id: Int, appliance: AppliancesModel): AppliancesModel {
        val updates = mutableListOf<String>()
        val params = mutableListOf<Any>()

        if (appliance.type != null) {
            updates.add("type = ?")
            params.add(appliance.type)
        }
        if (appliance.brand != null) {
            updates.add("brand = ?")
            params.add(appliance.brand)
        }
        if (appliance.model != null) {
            updates.add("model = ?")
            params.add(appliance.model!!)
        }

        // Si no hay campos para actualizar, retornamos igual
        if (updates.isEmpty()) return appliance

        val sql = "UPDATE appliances SET ${updates.joinToString(", ")} WHERE id = ?"
        params.add(id)

        jdbcTemplate.update(sql, *params.toTypedArray())

        return appliance
    }

    fun list5(id: Int): Boolean {
        val sql = "DELETE from appliances WHERE id = ?"
        val filas= jdbcTemplate.update (sql, id )
        return filas>0
    }
}
