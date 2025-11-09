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
        Schema::create('appliances', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')->constrained()->onDelete('cascade');
            $table->string('type');  // tipo de electrodoméstico
            $table->string('brand')->nullable();  // marca
            $table->string('model')->nullable();  // modelo
            $table->date('purchase_date')->nullable();  // fecha de compra
            $table->string('image')->nullable();  // ruta a la imagen del electrodoméstico
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('appliances');
    }
};
