<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

/**
 * @OA\Schema(
 *     schema="Appliance",
 *     title="Electrodoméstico",
 *     description="Modelo de electrodoméstico",
 *     required={"id", "type", "user_id"},
 *     @OA\Property(property="id", type="integer", example=1),
 *     @OA\Property(property="user_id", type="integer", example=5),
 *     @OA\Property(property="type", type="string", example="lavadora"),
 *     @OA\Property(property="brand", type="string", example="LG"),
 *     @OA\Property(property="model", type="string", example="X100"),
 *     @OA\Property(property="purchase_date", type="string", format="date", example="2024-05-01"),
 *     @OA\Property(property="image", type="string", nullable=true, example="appliance_images/imagen.jpg"),
 *     @OA\Property(property="created_at", type="string", format="date-time"),
 *     @OA\Property(property="updated_at", type="string", format="date-time")
 * )
 */

class Appliance extends Model
{
    protected $fillable = [
        'type',
        'brand',
        'model',
        'purchase_date',
        'image',
        'user_id',
    ];

    public function user()
    {
        return $this->belongsTo(User::class);
    }
}
