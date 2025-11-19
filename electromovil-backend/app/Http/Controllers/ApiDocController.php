<?php

namespace App\Http\Controllers;

/**
 * @OA\Info(
 *     version="1.0.0",
 *     title="Documentación de ElectroElite",
 *     description="API construida con Laravel y documentada con Swagger",
 *     @OA\Contact(
 *         email="soporte@tuservicio.com",
 *         name="Soporte Técnico"   
 *     )
 * )
 *
 * @OA\Server(
 *     url=L5_SWAGGER_CONST_HOST,
 *     description="Servidor local"
 * )
 */
class ApiDocController extends Controller
{
    // Este archivo solo se usa para documentación
}
