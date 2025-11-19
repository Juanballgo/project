package com.example.demo.Controllers

import com.example.demo.Models.AppliancesModel
import com.example.demo.Services.AppliancesService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AppliancesController {

    @Autowired
    lateinit var AppliancesService: AppliancesService

    @GetMapping("/appliances")
    fun appliances(): List<String> {
        return AppliancesService.list1()
    }
    @GetMapping("/appliances/{id}")
    fun search(@PathVariable id: Int): List<String> {
        return AppliancesService.list3(id)
    }

    @PostMapping("/appliances")
    fun appliance(@RequestBody appliance: AppliancesModel): AppliancesModel {
        return AppliancesService.list2(appliance)
    }
    @PutMapping("/appliances/{id}")
    fun update(@PathVariable id: Int, @RequestBody appliance: AppliancesModel): AppliancesModel {
        return AppliancesService.list4(id, appliance)
    }
    @DeleteMapping("/appliances/{id}")
    fun delete(@PathVariable id: Int): String {
        return if (AppliancesService.list5(id)){
            "Electrodomestico eliminado $id correctamente "
        } else {
            "Electrodomestico con id $id no encontrado"
        }
    }
}