<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('servicios', function (Blueprint $table) {
            $table->id();
            $table->foreignId('cliente_id')->constrained('users');
            $table->foreignId('tecnico_id')->nullable()->constrained('users');
            $table->string('tipo_equipo'); // lavadora o nevera
            $table->string('marca');
            $table->string('modelo');
            $table->text('descripcion_problema');
            $table->enum('estado', ['pendiente', 'en_proceso', 'completado', 'cancelado'])->default('pendiente');
            $table->text('diagnostico')->nullable();
            $table->text('solucion')->nullable();
            $table->decimal('costo', 8, 2)->nullable();
            $table->date('fecha_solicitud');
            $table->date('fecha_atendido')->nullable();
            $table->date('fecha_completado')->nullable();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('servicios');
    }
};
