<?php

namespace App\Http\Controllers;

use App\Models\Appliance;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Storage;

/**
 * @OA\Tag(
 *     name="Appliances",
 *     description="Gestión de electrodomésticos del usuario"
 * )
 */
class ApplianceController extends Controller
{
    /**
     * @OA\Get(
     *     path="/api/appliances",
     *     summary="Listar electrodomésticos del usuario autenticado",
     *     tags={"Appliances"},
     *     security={{"sanctum":{}}},
     *     @OA\Response(
     *         response=200,
     *         description="Lista de electrodomésticos",
     *         @OA\JsonContent(type="array", @OA\Items(ref="#/components/schemas/Appliance"))
     *     )
     * )
     */
    public function index()
    {
        $user = Auth::user();
        return response()->json($user->appliances);
    }

    /**
     * @OA\Post(
     *     path="/api/appliances",
     *     summary="Registrar un nuevo electrodoméstico",
     *     tags={"Appliances"},
     *     security={{"sanctum":{}}},
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\MediaType(
     *             mediaType="multipart/form-data",
     *             @OA\Schema(
     *                 required={"type"},
     *                 @OA\Property(property="type", type="string", enum={"lavadora", "nevera"}),
     *                 @OA\Property(property="brand", type="string"),
     *                 @OA\Property(property="model", type="string"),
     *                 @OA\Property(property="purchase_date", type="string", format="date"),
     *                 @OA\Property(property="image", type="string", format="binary")
     *             )
     *         )
     *     ),
     *     @OA\Response(
     *         response=201,
     *         description="Electrodoméstico creado",
     *         @OA\JsonContent(ref="#/components/schemas/Appliance")
     *     )
     * )
     */
    public function store(Request $request)
    {
        $request->validate([
            'type' => 'required|in:lavadora,nevera',
            'brand' => 'nullable|string|max:255',
            'model' => 'nullable|string|max:255',
            'purchase_date' => 'nullable|date',
            'image' => 'nullable|image|max:2048',
        ]);

        $imagePath = null;
        if ($request->hasFile('image')) {
            $imagePath = $request->file('image')->store('appliance_images', 'public');
        }

        $appliance = Appliance::create([
            'user_id' => Auth::id(),
            'type' => $request->type,
            'brand' => $request->brand,
            'model' => $request->model,
            'purchase_date' => $request->purchase_date,
            'image' => $imagePath,
        ]);

        return response()->json($appliance, 201);
    }

    /**
     * @OA\Delete(
     *     path="/api/appliances/{id}",
     *     summary="Eliminar un electrodoméstico",
     *     tags={"Appliances"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\Response(response=200, description="Electrodoméstico eliminado"),
     *     @OA\Response(response=404, description="Electrodoméstico no encontrado")
     * )
     */
    public function destroy($id)
    {
        $appliance = Appliance::where('id', $id)
            ->where('user_id', Auth::id())
            ->firstOrFail();

        $appliance->delete();

        return response()->json(['message' => 'Electrodoméstico eliminado'], 200);
    }

    /**
     * @OA\Put(
     *     path="/api/appliances/{id}",
     *     summary="Actualizar un electrodoméstico",
     *     tags={"Appliances"},
     *     security={{"sanctum":{}}},
     *     @OA\Parameter(
     *         name="id",
     *         in="path",
     *         required=true,
     *         @OA\Schema(type="integer")
     *     ),
     *     @OA\RequestBody(
     *         required=true,
     *         @OA\JsonContent(
     *             @OA\Property(property="type", type="string", enum={"lavadora", "nevera"}),
     *             @OA\Property(property="brand", type="string"),
     *             @OA\Property(property="model", type="string"),
     *             @OA\Property(property="purchaseDate", type="string", format="date"),
     *             @OA\Property(property="image", type="string")
     *         )
     *     ),
     *     @OA\Response(response=200, description="Electrodoméstico actualizado"),
     *     @OA\Response(response=403, description="No autorizado"),
     *     @OA\Response(response=404, description="No encontrado")
     * )
     */
    public function update(Request $request, Appliance $appliance)
    {
        if ($appliance->user_id !== Auth::id()) {
            return response()->json(['error' => 'No autorizado'], 403);
        }

        $request->validate([
            'type' => 'sometimes|required|in:lavadora,nevera',
            'brand' => 'sometimes|nullable|string|max:255',
            'model' => 'sometimes|nullable|string|max:255',
            'purchaseDate' => 'sometimes|nullable|date',
            'image' => 'sometimes|nullable|string',
        ]);

        $appliance->update($request->only([
            'type', 'brand', 'model', 'purchaseDate', 'image'
        ]));

        return response()->json($appliance);
    }
}
