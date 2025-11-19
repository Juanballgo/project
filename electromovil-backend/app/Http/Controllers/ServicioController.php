<?php

namespace App\Http\Controllers;

use App\Models\Servicio;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

/**
 * @OA\Tag(
 *     name="Servicios",
 *     description="Operaciones relacionadas con los servicios"
 * )
 */
class ServicioController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth:sanctum');
    }

    /**
     * @OA\Get(
     *     path="/api/servicios",
     *     summary="Listar servicios según el rol",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="estado", in="query", @OA\Schema(type="string")),
     *     @OA\Parameter(name="tipo_equipo", in="query", @OA\Schema(type="string")),
     *     @OA\Parameter(name="per_page", in="query", @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Lista de servicios")
     * )
     */
    public function index(Request $request)
    {
        $user = Auth::user();
        $query = Servicio::query();

        if ($user->isAdmin()) {
            $query->with(['cliente', 'tecnico']);
        } elseif ($user->isTecnico()) {
            $query->where('tecnico_id', $user->id)->with('cliente');
        } else {
            $query->where('cliente_id', $user->id)->with('tecnico');
        }

        if ($request->has('estado')) {
            $query->where('estado', $request->estado);
        }
        if ($request->has('tipo_equipo')) {
            $query->where('tipo_equipo', $request->tipo_equipo);
        }

        $query->orderBy('fecha_solicitud', 'desc');

        $servicios = $request->has('per_page') ? $query->paginate($request->per_page) : $query->get();
        return response()->json($servicios);
    }

    /**
     * @OA\Post(
     *     path="/api/servicios",
     *     summary="Crear servicio",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"tipo_equipo","marca","modelo","descripcion_problema"},
     *             @OA\Property(property="cliente_id", type="integer"),
     *             @OA\Property(property="tipo_equipo", type="string"),
     *             @OA\Property(property="marca", type="string"),
     *             @OA\Property(property="modelo", type="string"),
     *             @OA\Property(property="descripcion_problema", type="string"),
     *             @OA\Property(property="tecnico_id", type="integer"),
     *             @OA\Property(property="costo", type="number")
     *         )
     *     ),
     *     @OA\Response(response=201, description="Servicio creado")
     * )
     */
    public function store(Request $request)
    {
        $user = Auth::user();

        if ($user->isAdmin()) {
            $request->validate([
                'cliente_id' => 'required|exists:users,id,role,cliente',
                'tipo_equipo' => 'required|in:lavadora,nevera',
                'marca' => 'required|string|max:100',
                'modelo' => 'required|string|max:100',
                'descripcion_problema' => 'required|string',
                'tecnico_id' => 'nullable|exists:users,id,role,tecnico',
                'costo' => 'nullable|numeric|min:0',
            ]);

            $servicio = Servicio::create([
                'cliente_id' => $request->cliente_id,
                'tipo_equipo' => $request->tipo_equipo,
                'marca' => $request->marca,
                'modelo' => $request->modelo,
                'descripcion_problema' => $request->descripcion_problema,
                'fecha_solicitud' => now(),
                'tecnico_id' => $request->tecnico_id,
                'costo' => $request->costo,
            ]);

            return response()->json($servicio, 201);
        }

        if (!$user->isCliente()) {
            return response()->json(['message' => 'No autorizado'], 403);
        }

        $request->validate([
            'tipo_equipo' => 'required|in:lavadora,nevera',
            'marca' => 'required|string|max:100',
            'modelo' => 'required|string|max:100',
            'descripcion_problema' => 'required|string',
        ]);

        $servicio = Servicio::create([
            'cliente_id' => $user->id,
            'tipo_equipo' => $request->tipo_equipo,
            'marca' => $request->marca,
            'modelo' => $request->modelo,
            'descripcion_problema' => $request->descripcion_problema,
            'fecha_solicitud' => now(),
        ]);

        return response()->json($servicio, 201);
    }

    /**
     * @OA\Get(
     *     path="/api/servicios/{id}",
     *     summary="Mostrar un servicio",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Servicio encontrado"),
     *     @OA\Response(response=403, description="No autorizado")
     * )
     */
    public function show(Servicio $servicio)
    {
        $user = Auth::user();
        if ($user->isAdmin() || $servicio->cliente_id == $user->id || $servicio->tecnico_id == $user->id) {
            return response()->json($servicio);
        }
        return response()->json(['message' => 'No autorizado'], 403);
    }

    /**
     * @OA\Put(
     *     path="/api/servicios/{id}",
     *     summary="Actualizar servicio",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, @OA\Schema(type="integer")),
     *     @OA\RequestBody(@OA\JsonContent()),
     *     @OA\Response(response=200, description="Servicio actualizado")
     * )
     */
    public function update(Request $request, Servicio $servicio)
    {
        $user = Auth::user();

        if ($user->isAdmin()) {
            $validated = $request->validate([
                'tecnico_id' => 'nullable|exists:users,id',
                'estado' => 'in:pendiente,en_proceso,completado,cancelado',
                'diagnostico' => 'nullable|string',
                'solucion' => 'nullable|string',
                'costo' => 'nullable|numeric',
                'fecha_atendido' => 'nullable|date',
                'fecha_completado' => 'nullable|date',
            ]);
            $servicio->update($validated);

        } elseif ($user->isTecnico() && $servicio->tecnico_id == $user->id) {
            $validated = $request->validate([
                'estado' => 'in:en_proceso,completado,cancelado',
                'diagnostico' => 'nullable|string',
                'solucion' => 'nullable|string',
                'costo' => 'nullable|numeric',
                'fecha_completado' => 'nullable|date',
            ]);
            if ($request->estado == 'en_proceso' && is_null($servicio->fecha_atendido)) {
                $validated['fecha_atendido'] = now();
            }
            $servicio->update($validated);

        } elseif ($user->isCliente() && $servicio->cliente_id == $user->id) {
            if ($request->estado == 'cancelado' && $servicio->estado == 'pendiente') {
                $servicio->update(['estado' => 'cancelado']);
            } else {
                return response()->json(['message' => 'Solo puedes cancelar servicios pendientes'], 403);
            }

        } else {
            return response()->json(['message' => 'No autorizado, contacte al administrador'], 403);
        }

        return response()->json($servicio);
    }

    /**
     * @OA\Delete(
     *     path="/api/servicios/{id}",
     *     summary="Eliminar servicio",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, @OA\Schema(type="integer")),
     *     @OA\Response(response=204, description="Servicio eliminado")
     * )
     */
    public function destroy(Servicio $servicio)
    {
        $user = Auth::user();
        if (!$user->isAdmin()) {
            return response()->json(['message' => 'Solo el admin puede eliminar servicios'], 403);
        }
        $servicio->delete();
        return response()->json(null, 204);
    }

    /**
     * @OA\Post(
     *     path="/api/servicios/{id}/asignar-tecnico",
     *     summary="Asignar técnico a servicio",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, @OA\Schema(type="integer")),
     *     @OA\RequestBody(@OA\JsonContent(required={"tecnico_id"}, @OA\Property(property="tecnico_id", type="integer"))),
     *     @OA\Response(response=200, description="Técnico asignado")
     * )
     */
    public function asignarTecnico(Request $request, Servicio $servicio)
    {
        $user = Auth::user();
        if (!$user->isAdmin()) {
            return response()->json(['message' => 'Solo el administrador puede asignar técnicos'], 403);
        }
        $request->validate([
            'tecnico_id' => 'required|exists:users,id,role,tecnico'
        ]);
        $servicio->update([
            'tecnico_id' => $request->tecnico_id,
            'estado' => 'asignado',
            'fecha_asignacion' => now()
        ]);
        return response()->json([
            'message' => 'Técnico asignado correctamente',
            'servicio' => $servicio->load('tecnico')
        ]);
    }

    /**
     * @OA\Get(
     *     path="/api/servicios/tecnico/mis-servicios",
     *     summary="Ver servicios asignados a un técnico",
     *     tags={"Servicios"},
     *     security={{"sanctum":{}}},
     *     @OA\Response(response=200, description="Lista de servicios asignados")
     * )
     */
    public function misServiciosTecnico(Request $request)
    {
        $tecnico = $request->user();
        if ($tecnico->role !== 'tecnico') {
            return response()->json(['error' => 'No autorizado'], 403);
        }
        $servicios = Servicio::with('cliente')
            ->where('tecnico_id', $tecnico->id)
            ->get();
        return response()->json($servicios);
    }
}
