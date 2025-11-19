package com.example.demo.Controllers

import com.example.demo.Services.ServiciosService
import com.example.demo.Models.ServiceModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class ServiceController {

    @Autowired
    lateinit var ServiceController: ServiciosService

    // GET -> Listar todos los servicios
    @GetMapping("/servicios")
    fun Serviceservicios(): List<ServiceModel> {
        return ServiceController.Servislist1()
    }

    // GET -> Obtener servicio por ID
    @GetMapping("/servicios/{id}")
    fun getServicioById(@PathVariable id: Int): ServiceModel? {
        return ServiceController.Servislist3(id)
    }

    // POST -> Crear un nuevo servicio
    @PostMapping("/servicios")
    fun Serviceservicio(@RequestBody servicio: ServiceModel): ServiceModel {
        return ServiceController.Servislist2(servicio)
    }

    // PUT -> Actualizar servicio por id
    @PutMapping("/servicios/{id}")
    fun Serviceupdate(@PathVariable id: Int, @RequestBody servicio: ServiceModel): ServiceModel {
        return ServiceController.Servislist4(id, servicio)
    }

    // DELETE -> Eliminar servicio por id
    @DeleteMapping("/servicios/{id}")
    fun Servicedelete(@PathVariable id: Int): String {
        return if (ServiceController.Servislist5(id)) {
            "Servicio con id $id eliminado correctamente"
        } else {
            "Servicio con id $id no encontrado"
        }
    }

}