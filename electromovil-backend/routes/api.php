<?php
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\AuthController;
use App\Http\Controllers\ServicioController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\ApplianceController;

// Rutas públicas
Route::post('/register', [AuthController::class, 'register']);
Route::post('/login', [AuthController::class, 'login'])->name('login');
Route::post('/forgotpassword', [AuthController::class, 'sendResetLinkEmail']);
Route::post('/reset-password', [AuthController::class, 'resetPassword']);
 
// Rutas protegidas

Route::middleware('auth:sanctum')->get('/check-auth', function (Request $request) {
    return response()->json([
        'authenticated' => true,
        'user' => $request->user()
    ]);
});

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/logout', [AuthController::class, 'logout']);

    //consulta de datos del mismo cliente
    Route::get('/me', [AuthController::class, 'me']);

    // consulta de tecnicos 
    Route::get('/users/by-role', [UserController::class, 'indexByRole']);
     
    // ruta tecnico
    
    Route::get('/mis', [ServicioController::class, 'misServiciosTecnico']);
    // Rutas de servicios
    Route::apiResource('servicios', ServicioController::class);
        
    // Rutas de usuarios
    Route::apiResource('users', UserController::class);

    //Ruta de eliminación de usuarios
    Route::delete('users/{user}', [UserController::class, 'destroy']);
 
    // Rutas de admin // asignacion de tecnicos
    //Route::post('/servicios/{servicio}/asignar-tecnico', [ServicioController::class, 'asignarTecnico']);
    Route::get('/tecnicos-disponibles', [UserController::class, 'tecnicosDisponibles']);

    //  Rutas de electrodomésticos protegidas
    Route::apiResource('appliances', ApplianceController::class);
});