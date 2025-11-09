<?php

namespace Database\Seeders;

use App\Models\Servicio;
use App\Models\User;
use Illuminate\Database\Seeder;

class ServiciosTableSeeder extends Seeder
{
    public function run()
    {
        // Obtener clientes y técnicos existentes
        $clientes = User::where('role', 'cliente')->take(5)->get();
        $tecnicos = User::where('role', 'tecnico')->take(3)->get();

        // Tipos de equipos y problemas comunes
        $equipos = ['lavadora', 'nevera'];
        $marcas = ['Samsung', 'LG', 'Mabe', 'Whirlpool', 'GE'];
        $problemas = [
            'No enciende', 
            'No centrifuga', 
            'Tiene fugas de agua',
            'Hace ruido extraño',
            'No enfría'
        ];

        // Servicios
        foreach (range(1, 20) as $index) {
            Servicio::create([
                'cliente_id' => $clientes->random()->id,
                'tecnico_id' => rand(0, 1) ? $tecnicos->random()->id : null, // 50% con técnico asignado
                'tipo_equipo' => $equipos[array_rand($equipos)],
                'marca' => $marcas[array_rand($marcas)],
                'modelo' => 'MOD-' . rand(1000, 9999),
                'descripcion_problema' => $problemas[array_rand($problemas)],
                'estado' => ['pendiente', 'en_proceso', 'completado'][rand(0, 2)],
                'fecha_solicitud' => now()->subDays(rand(1, 30)),
                'fecha_atendido' => rand(0, 1) ? now()->subDays(rand(1, 15)) : null,
                'fecha_completado' => rand(0, 1) ? now()->subDays(rand(1, 5)) : null,
                'costo' => rand(0, 1) ? rand(50, 500) : null
            ]);
        }
    }
}