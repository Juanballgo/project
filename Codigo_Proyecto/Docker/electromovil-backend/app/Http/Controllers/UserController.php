<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

/**
 * @OA\Tag(
 *     name="Users",
 *     description="Operaciones relacionadas con los usuarios"
 * )
 */
class UserController extends Controller
{
    public function __construct()
    {
        $this->middleware('auth:sanctum');
    }

    /**
     * @OA\Get(
     *     path="/api/users",
     *     summary="Listar usuarios según el rol del autenticado",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\Response(
     *         response=200,
     *         description="Lista de usuarios",
     *         @OA\JsonContent(type="array", @OA\Items(ref="#/components/schemas/User"))
     *     )
     * )
     */
    public function index()
    {
        $user = Auth::user();

        if ($user->isAdmin()) {
            $users = User::all();
        } elseif ($user->isTecnico()) {
            $users = User::whereIn('role', ['cliente', 'tecnico'])->get();
        } else {
            $users = User::where('id', $user->id)->get();
        }

        return response()->json($users);
    }

    /**
     * @OA\Post(
     *     path="/api/users",
     *     summary="Crear un nuevo usuario",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             required={"name","email","password","role","phone","address"},
     *             @OA\Property(property="name", type="string"),
     *             @OA\Property(property="email", type="string", format="email"),
     *             @OA\Property(property="password", type="string", format="password"),
     *             @OA\Property(property="role", type="string", enum={"admin","tecnico","cliente"}),
     *             @OA\Property(property="phone", type="string"),
     *             @OA\Property(property="address", type="string")
     *         )
     *     ),
     *     @OA\Response(
     *         response=201,
     *         description="Usuario creado",
     *         @OA\JsonContent(ref="#/components/schemas/User")
     *     )
     * )
     */
    public function store(Request $request)
    {
        $request->validate([
            'name' => 'required|string|max:255',
            'email' => 'required|string|email|max:255|unique:users',
            'password' => 'required|string|min:8',
            'role' => 'required|in:admin,tecnico,cliente',
            'phone' => 'required|string|max:20',
            'address' => 'required|string|max:255'
        ]);

        $user = User::create([
            'name' => $request->name,
            'email' => $request->email,
            'password' => bcrypt($request->password),
            'role' => $request->role,
            'phone' => $request->phone,
            'address' => $request->address
        ]);

        return response()->json($user, 201);
    }

    /**
     * @OA\Get(
     *     path="/api/users/{id}",
     *     summary="Mostrar un usuario",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, @OA\Schema(type="integer")),
     *     @OA\Response(response=200, description="Usuario encontrado", @OA\JsonContent(ref="#/components/schemas/User")),
     *     @OA\Response(response=403, description="No autorizado")
     * )
     */
    public function show(User $user)
    {
        $currentUser = Auth::user();

        if ($currentUser->isAdmin() || $currentUser->isTecnico() || $currentUser->id == $user->id) {
            return response()->json($user);
        }

        return response()->json(['message' => 'No autorizado'], 403);
    }

    /**
     * @OA\Put(
     *     path="/api/users/{id}",
     *     summary="Actualizar un usuario",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="id", in="path", required=true, @OA\Schema(type="integer")),
     *     @OA\RequestBody(
     *         @OA\JsonContent(
     *             @OA\Property(property="name", type="string"),
     *             @OA\Property(property="email", type="string", format="email"),
     *             @OA\Property(property="password", type="string"),
     *             @OA\Property(property="role", type="string"),
     *             @OA\Property(property="phone", type="string"),
     *             @OA\Property(property="address", type="string")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Usuario actualizado", @OA\JsonContent(ref="#/components/schemas/User"))
     * )
     */
    public function update(Request $request, User $user)
    {
        $request->validate([
            'name' => 'sometimes|string|max:255',
            'email' => 'sometimes|string|email|max:255|unique:users,email,' . $user->id,
            'password' => 'sometimes|string|min:8',
            'role' => 'sometimes|in:admin,tecnico,cliente',
            'phone' => 'sometimes|string|max:20',
            'address' => 'sometimes|string|max:255'
        ]);

        $data = $request->all();

        if (isset($data['role']) && auth()->user()->role !== 'admin') {
            unset($data['role']);
        }

        if ($request->has('password')) {
            $data['password'] = bcrypt($request->password);
        }

        $user->update($data);

        return response()->json($user);
    }

    /**
     * @OA\Delete(
     *     path="/api/users/{id}",
     *     summary="Eliminar un usuario",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(
     *         response=204,
     *         description="Usuario eliminado"
     *     ),
     *     @OA\Response(
     *         response=403,
     *         description="Acceso denegado"
     *     )
     * )
     */
    public function destroy($id)
    {
        if (!auth()->user()->isAdmin()) {
            return response()->json(['message' => 'Acceso denegado, no eres administrador'], 403);
        }

        $user = User::findOrFail($id);
        $user->delete();

        return response()->noContent();
    }

    /**
     * @OA\Get(
     *     path="/api/users/by-role",
     *     summary="Listar usuarios por rol",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(name="role", in="query", required=true, @OA\Schema(type="string")),
     *     @OA\Response(response=200, description="Usuarios filtrados por rol", @OA\JsonContent(type="array", @OA\Items(ref="#/components/schemas/User")))
     * )
     */
    public function indexByRole(Request $request)
    {
        $role = $request->query('role');
        $users = User::where('role', $role)->get();
        return response()->json($users);
    }

    /**
     * @OA\Get(
     *     path="/api/users/tecnicos/disponibles",
     *     summary="Listar técnicos disponibles (solo admin)",
     *     tags={"Users"},
     *     security={{"sanctum":{}}},
     *     @OA\Response(response=200, description="Técnicos disponibles", @OA\JsonContent(type="object", @OA\Property(property="tecnicos", type="array", @OA\Items(ref="#/components/schemas/User")))),
     *     @OA\Response(response=403, description="No autorizado")
     * )
     */
    public function tecnicosDisponibles()
    {
        if (!auth()->user()->isAdmin()) {
            return response()->json(['message' => 'No autorizado'], 403);
        }

        return response()->json([
            'tecnicos' => User::where('role', 'tecnico')
                ->select('id', 'name', 'email')
                ->get()
        ]);
    }
}
