<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use App\Models\User;
use Illuminate\Support\Facades\Hash;

class UsersTableSeeder extends Seeder
{
    public function run()
    {
        // Admin
        User::create([
            'name' => 'Admin',
            'email' => 'admin@electromovil.com',
            'password' => Hash::make('password'),
            'role' => 'admin',
            'phone' => '3125767402',
            'address' => 'Calle Admin #123'
        ]);

        // Técnico
        User::create([
            'name' => 'Técnico Uno',
            'email' => 'tecnico@example.com',
            'password' => Hash::make('password'),
            'role' => 'tecnico',
            'phone' => '0987654321',
            'address' => 'Calle Tecnico #456'
        ]);

        // Cliente
        User::create([
            'name' => 'Cliente Uno',
            'email' => 'cliente@example.com',
            'password' => Hash::make('password'),
            'role' => 'cliente',
            'phone' => '5555555555',
            'address' => 'Calle Cliente #789'
        ]);
    }
}
