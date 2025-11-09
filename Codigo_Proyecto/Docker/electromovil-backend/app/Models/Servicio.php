<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

// Modelo Servicio: representa un servicio solicitado por un cliente y atendido por un técnico
class Servicio extends Model
{
    use HasFactory;

    // Campos que se pueden asignar masivamente
    protected $fillable = [
        'cliente_id',            // ID del cliente que solicita el servicio
        'tecnico_id',            // ID del técnico asignado al servicio
        'fecha_asignacion',      // Fecha en que se asignó el técnico
        'tipo_equipo',           // Tipo de equipo a reparar
        'marca',                 // Marca del equipo
        'modelo',                // Modelo del equipo
        'descripcion_problema',  // Descripción del problema reportado
        'estado',                // Estado actual del servicio (ej: pendiente, en proceso, completado)
        'diagnostico',           // Diagnóstico realizado por el técnico
        'solucion',              // Solución aplicada al problema
        'costo',                 // Costo del servicio
        'fecha_solicitud',       // Fecha en que el cliente solicitó el servicio
        'fecha_atendido',        // Fecha en que el técnico atendió el servicio
        'fecha_completado'       // Fecha en que el servicio fue completado
    ];

    // Campos que serán tratados como fechas por Eloquent
    protected $dates = [
        'fecha_asignacion',
        'fecha_solicitud',
        'fecha_atendido',
        'fecha_completado'
    ];

    // Relación: un servicio pertenece a un cliente (usuario)
    public function cliente()
    {
        return $this->belongsTo(User::class, 'cliente_id');
    }

    // Relación: un servicio pertenece a un técnico (usuario)
    public function tecnico()
    {
        return $this->belongsTo(User::class, 'tecnico_id');
    }
}