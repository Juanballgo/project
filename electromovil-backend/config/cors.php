<?php

return [

    'paths' => ['api/*', 'sanctum/csrf-cookie', 'login', 'register', 'lougout'],

    'allowed_methods' => ['*'],

    'allowed_origins' => ['http://localhost:3000'], // Conexion con React

    'allowed_origins_patterns' => [],

    'allowed_headers' => ['*'],

    'exposed_headers' => [],

    'max_age' => 0,

    'supports_credentials' => true,

];
