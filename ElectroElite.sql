-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-07-2025 a las 03:58:18
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `electromovil`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `appliances`
--

CREATE TABLE `appliances` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `type` varchar(255) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `purchase_date` date DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `appliances`
--

INSERT INTO `appliances` (`id`, `user_id`, `type`, `brand`, `model`, `purchase_date`, `image`, `created_at`, `updated_at`) VALUES
(8, 10, 'nevera', 'Samsung', 'x5yr', '2025-06-05', NULL, '2025-06-21 06:44:29', '2025-06-21 06:44:38'),
(9, 27, 'nevera', 'Samsung', '12315', '2025-06-11', NULL, '2025-06-27 05:24:48', '2025-06-27 05:25:02');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cache`
--

CREATE TABLE `cache` (
  `key` varchar(255) NOT NULL,
  `value` mediumtext NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `cache`
--

INSERT INTO `cache` (`key`, `value`, `expiration`) VALUES
('laravel_cache_5c785c036466adea360111aa28563bfd556b5fba', 'i:1;', 1750722650),
('laravel_cache_5c785c036466adea360111aa28563bfd556b5fba:timer', 'i:1750722650;', 1750722650);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cache_locks`
--

CREATE TABLE `cache_locks` (
  `key` varchar(255) NOT NULL,
  `owner` varchar(255) NOT NULL,
  `expiration` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `failed_jobs`
--

CREATE TABLE `failed_jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `uuid` varchar(255) NOT NULL,
  `connection` text NOT NULL,
  `queue` text NOT NULL,
  `payload` longtext NOT NULL,
  `exception` longtext NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `jobs`
--

CREATE TABLE `jobs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `queue` varchar(255) NOT NULL,
  `payload` longtext NOT NULL,
  `attempts` tinyint(3) UNSIGNED NOT NULL,
  `reserved_at` int(10) UNSIGNED DEFAULT NULL,
  `available_at` int(10) UNSIGNED NOT NULL,
  `created_at` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `job_batches`
--

CREATE TABLE `job_batches` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `total_jobs` int(11) NOT NULL,
  `pending_jobs` int(11) NOT NULL,
  `failed_jobs` int(11) NOT NULL,
  `failed_job_ids` longtext NOT NULL,
  `options` mediumtext DEFAULT NULL,
  `cancelled_at` int(11) DEFAULT NULL,
  `created_at` int(11) NOT NULL,
  `finished_at` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '0001_01_01_000001_create_cache_table', 1),
(2, '0001_01_01_000002_create_jobs_table', 1),
(3, '2025_04_09_180537_create_personal_access_tokens_table', 1),
(4, '2025_04_09_180737_create_users_table', 1),
(5, '2025_04_09_181054_create_servicios_table', 1),
(6, '2025_04_09_184219_create_sessions_table', 1),
(7, '2025_06_07_201220_create_appliances_table', 2),
(8, '2025_04_14_130613_create_personal_access_tokens_table', 3),
(9, '2025_06_20_203710_create_password_reset_tokens_table', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `password_reset_tokens`
--

INSERT INTO `password_reset_tokens` (`email`, `token`, `created_at`) VALUES
('juanpablo@gmail.com', '$2y$12$kkRJ/L2wZWu5sKOURHvHl.U3.kX0TVfGg1tO1/HrgrrAZaVDRxomi', '2025-06-21 01:52:47'),
('juanpoxc@gmail.com', '$2y$12$.NNghpMXZ/1ras5hUQ7dtu6/dxEcHWAYZrQTKl..TGiMZBwPA7JN2', '2025-06-24 04:52:13');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `personal_access_tokens`
--

INSERT INTO `personal_access_tokens` (`id`, `tokenable_type`, `tokenable_id`, `name`, `token`, `abilities`, `last_used_at`, `expires_at`, `created_at`, `updated_at`) VALUES
(5, 'App\\Models\\User', 1, 'api-token', '6b4109e71c165ea8a83128cb5ab0f8419b17053221bdec2faae88f5da4503325', '[\"*\"]', '2025-06-24 05:17:31', NULL, '2025-06-24 05:17:30', '2025-06-24 05:17:31'),
(6, 'App\\Models\\User', 1, 'api-token', 'd071836371909b0554b013152145c7193f5f2120d65075ebf5404f016d65b8a9', '[\"*\"]', '2025-06-24 05:17:56', NULL, '2025-06-24 05:17:55', '2025-06-24 05:17:56'),
(7, 'App\\Models\\User', 10, 'api-token', 'f06ab1719c305c4af710ea0aa58aa84d15cb2119aa1cd711e7a40c51cb4c4a1b', '[\"*\"]', '2025-06-24 05:18:59', NULL, '2025-06-24 05:18:57', '2025-06-24 05:18:59'),
(9, 'App\\Models\\User', 1, 'api-token', '2267ec182e3b537eaa6ffc01220844374541ace95d1567ac818cc3555d9cf51e', '[\"*\"]', '2025-06-25 19:47:56', NULL, '2025-06-25 19:47:55', '2025-06-25 19:47:56'),
(10, 'App\\Models\\User', 1, 'api-token', 'c6fda9a79b13e5e315805dc4519032fd7281933d3b59d2419deb88e35d791cb7', '[\"*\"]', '2025-06-25 19:59:17', NULL, '2025-06-25 19:59:16', '2025-06-25 19:59:17'),
(11, 'App\\Models\\User', 1, 'api-token', 'f26cc9a2782a5845245c50144638a4a6420090e839c1f3686d25ac9ffb486e20', '[\"*\"]', '2025-06-25 20:03:04', NULL, '2025-06-25 20:03:03', '2025-06-25 20:03:04'),
(16, 'App\\Models\\User', 27, 'auth_token', '0837c043dadac4a82a74d0ba1ed3f36a8f00526bba17960f97afc148f12c68cd', '[\"*\"]', NULL, NULL, '2025-06-27 05:23:25', '2025-06-27 05:23:25'),
(19, 'App\\Models\\User', 28, 'auth_token', 'b8555501d39f820b8bbde50b7a294d546d0f64e6afd6a39a5c5a1079e359c7bc', '[\"*\"]', NULL, NULL, '2025-06-27 05:30:00', '2025-06-27 05:30:00'),
(33, 'App\\Models\\User', 1, 'api-token', '39b0b69bda9f4d7b7667b8948b0cdd534e297ed812e27c703b4360c6493d9fe8', '[\"*\"]', '2025-07-03 05:50:18', NULL, '2025-07-03 05:47:36', '2025-07-03 05:50:18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicios`
--

CREATE TABLE `servicios` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `cliente_id` bigint(20) UNSIGNED NOT NULL,
  `tecnico_id` bigint(20) UNSIGNED DEFAULT NULL,
  `tipo_equipo` varchar(255) NOT NULL,
  `marca` varchar(255) NOT NULL,
  `modelo` varchar(255) NOT NULL,
  `descripcion_problema` text NOT NULL,
  `estado` enum('pendiente','en_proceso','completado','cancelado') NOT NULL DEFAULT 'pendiente',
  `diagnostico` text DEFAULT NULL,
  `solucion` text DEFAULT NULL,
  `costo` decimal(8,2) DEFAULT NULL,
  `fecha_solicitud` date NOT NULL,
  `fecha_atendido` date DEFAULT NULL,
  `fecha_completado` date DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `servicios`
--

INSERT INTO `servicios` (`id`, `cliente_id`, `tecnico_id`, `tipo_equipo`, `marca`, `modelo`, `descripcion_problema`, `estado`, `diagnostico`, `solucion`, `costo`, `fecha_solicitud`, `fecha_atendido`, `fecha_completado`, `created_at`, `updated_at`) VALUES
(1, 3, 2, 'lavadora', 'Mabe', 'MOD-4280', 'Hace ruido extraño', 'completado', NULL, NULL, 373.00, '2025-03-14', '2025-03-30', NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(2, 3, 2, 'nevera', 'GE', 'MOD-4391', 'No centrifuga', 'pendiente', NULL, NULL, NULL, '2025-03-25', '2025-03-26', '2025-04-06', '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(3, 3, 11, 'nevera', 'Mabe', 'MOD-7727', 'Hace ruido extraño', 'completado', 'completado', 'se cambia motor', 900000.00, '2025-03-29', '2025-03-29', NULL, '2025-04-10 05:47:02', '2025-06-20 03:08:46'),
(4, 3, 11, 'lavadora', 'Whirlpool', 'MOD-4860', 'No enfría', 'en_proceso', 'problema encontrado', 'cliente solicita nuevamente renovar su visita', 80000.00, '2025-03-26', '2025-03-31', '2025-04-09', '2025-04-10 05:47:02', '2025-06-20 21:31:39'),
(5, 3, 2, 'lavadora', 'Whirlpool', 'MOD-6203', 'No enciende', 'pendiente', NULL, NULL, 95.00, '2025-03-15', NULL, NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(6, 3, 11, 'nevera', 'LG', 'MOD-9190', 'Hace ruido extraño', 'cancelado', 'cancelado', 'cliente ya reparo', NULL, '2025-03-23', '2025-03-26', NULL, '2025-04-10 05:47:02', '2025-06-20 21:57:26'),
(7, 3, 2, 'lavadora', 'LG', 'MOD-7208', 'No centrifuga', 'en_proceso', NULL, NULL, 305.00, '2025-03-15', '2025-03-27', '2025-04-09', '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(8, 3, NULL, 'lavadora', 'GE', 'MOD-3688', 'No enfría', 'en_proceso', NULL, NULL, NULL, '2025-03-29', '2025-03-30', '2025-04-05', '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(9, 3, NULL, 'lavadora', 'GE', 'MOD-7327', 'Tiene fugas de agua', 'pendiente', NULL, NULL, 339.00, '2025-03-30', NULL, '2025-04-05', '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(10, 3, 2, 'lavadora', 'Whirlpool', 'MOD-6268', 'No centrifuga', 'completado', NULL, NULL, NULL, '2025-04-04', '2025-03-31', '2025-04-08', '2025-04-10 05:47:02', '2025-06-10 04:42:57'),
(11, 3, NULL, 'lavadora', 'Samsung', 'MOD-3438', 'No centrifuga', 'pendiente', NULL, NULL, NULL, '2025-04-06', '2025-03-28', NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(12, 3, NULL, 'lavadora', 'LG', 'MOD-5945', 'Hace ruido extraño', 'completado', NULL, NULL, NULL, '2025-03-22', '2025-04-05', NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(13, 3, NULL, 'nevera', 'Samsung', 'MOD-6196', 'No enciende', 'en_proceso', NULL, NULL, NULL, '2025-03-14', NULL, NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(14, 3, 2, 'nevera', 'Mabe', 'MOD-2397', 'No enciende', 'completado', NULL, NULL, NULL, '2025-04-02', NULL, '2025-04-07', '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(15, 3, NULL, 'nevera', 'Whirlpool', 'MOD-9864', 'Hace ruido extraño', 'en_proceso', NULL, NULL, 183.00, '2025-03-17', '2025-03-27', NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(16, 3, 2, 'nevera', 'Mabe', 'MOD-6627', 'Hace ruido extraño', 'completado', NULL, NULL, NULL, '2025-03-29', NULL, NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(17, 3, NULL, 'lavadora', 'GE', 'MOD-6721', 'Hace ruido extraño', 'pendiente', NULL, NULL, NULL, '2025-03-30', NULL, NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(18, 3, NULL, 'lavadora', 'GE', 'MOD-2741', 'Hace ruido extraño', 'en_proceso', NULL, NULL, 240.00, '2025-03-19', '2025-03-30', NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(19, 3, 2, 'nevera', 'GE', 'MOD-7409', 'No enciende', 'pendiente', NULL, NULL, NULL, '2025-03-25', NULL, NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(20, 3, NULL, 'nevera', 'Whirlpool', 'MOD-3486', 'Hace ruido extraño', 'en_proceso', NULL, NULL, NULL, '2025-03-27', NULL, '2025-04-09', '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(21, 6, NULL, 'lavadora', 'Samsung', 'WA1234', 'No centrifuga', 'pendiente', NULL, NULL, NULL, '2025-04-15', NULL, NULL, '2025-04-15 18:31:36', '2025-04-15 18:31:36'),
(22, 6, NULL, 'lavadora', 'LG', 'WA1234', 'No centrifuga', 'pendiente', NULL, NULL, NULL, '2025-04-15', NULL, NULL, '2025-04-15 18:49:35', '2025-04-15 18:49:35'),
(23, 6, NULL, 'lavadora', 'Mabe', 'WA1234', 'No centrifuga', 'en_proceso', NULL, NULL, NULL, '2025-04-15', NULL, NULL, '2025-04-15 19:28:29', '2025-06-09 20:47:22'),
(24, 6, NULL, 'lavadora', 'Mabe', 'WA1234', 'No centrifuga', 'pendiente', NULL, NULL, NULL, '2025-04-21', NULL, NULL, '2025-04-22 02:47:54', '2025-04-22 02:47:54'),
(25, 6, NULL, 'lavadora', 'Mabe', 'WA1234', 'No centrifuga', 'pendiente', NULL, NULL, NULL, '2025-04-22', NULL, NULL, '2025-04-22 05:02:20', '2025-04-22 05:02:20'),
(26, 6, NULL, 'lavadora', 'Mabe', 'WA1234', 'no enciende y no funciona el lavado', 'pendiente', NULL, NULL, NULL, '2025-04-22', NULL, NULL, '2025-04-23 04:47:19', '2025-04-23 04:47:19'),
(27, 10, NULL, 'lavadora', 'Mabe', 'WA1234', 'no enciende', 'en_proceso', NULL, NULL, NULL, '2025-04-23', NULL, NULL, '2025-04-23 06:20:03', '2025-06-10 04:28:40'),
(28, 10, 7, 'lavadora', 'LG', 'lg56', 'No enciende', 'completado', NULL, NULL, NULL, '2025-06-05', NULL, NULL, '2025-06-06 03:05:52', '2025-06-14 00:41:17'),
(29, 10, 4, 'nevera', 'Samsung', '2024', 'Nevecon no frost no congela', 'completado', NULL, NULL, NULL, '2025-06-05', NULL, NULL, '2025-06-06 04:23:50', '2025-06-14 00:42:23'),
(31, 17, NULL, 'nevera', 'Samsung', '2288', 'no congela', 'pendiente', NULL, NULL, NULL, '2025-06-05', NULL, NULL, '2025-06-06 04:39:51', '2025-06-06 04:39:51'),
(32, 10, 22, 'lavadora', 'adsd1', 'lasd123', 'no enciende', 'completado', NULL, NULL, 80000.00, '2025-06-13', NULL, NULL, '2025-06-14 01:41:10', '2025-06-17 04:19:29'),
(33, 14, NULL, 'lavadora', '13qwqew', 'asd', 'asd', 'pendiente', NULL, NULL, NULL, '2025-06-13', NULL, NULL, '2025-06-14 01:42:33', '2025-06-14 01:42:33'),
(34, 14, 4, 'nevera', 'asd', 'adsa', 'asd', 'pendiente', NULL, NULL, 99000.00, '2025-06-13', NULL, NULL, '2025-06-14 01:47:11', '2025-06-14 01:47:11'),
(35, 10, 22, 'nevera', '12', '12', '12', 'pendiente', NULL, NULL, 12.00, '2025-06-13', NULL, NULL, '2025-06-14 01:50:13', '2025-06-14 01:50:13'),
(36, 10, 11, 'lavadora', 'lg', '2024', 'lavadora con ruidos extraños', 'cancelado', 'cancelado', 'cliente ya reparo su lavadora', 0.00, '2025-06-16', '2025-06-19', NULL, '2025-06-17 04:41:38', '2025-06-20 21:08:10'),
(37, 27, NULL, 'nevera', 'Samsung', '2024', 'no enciende', 'en_proceso', NULL, NULL, NULL, '2025-06-27', NULL, NULL, '2025-06-27 05:25:38', '2025-06-27 05:33:25');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `payload` longtext NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('admin','tecnico','cliente') NOT NULL DEFAULT 'cliente',
  `phone` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `email_verified_at`, `password`, `role`, `phone`, `address`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'Admin', 'admin@electroelite.com', NULL, '$2y$12$4Y4LbWUM800qeuId1toqL.jttbkorzpk2ZzfOWPUpiQoeWSSShITa', 'admin', '3125767402', 'Calle Admin #123', NULL, '2025-04-10 05:47:01', '2025-07-02 00:05:54'),
(2, 'Tecnico ejemplo', 'tecnico@example.com', NULL, '$2y$12$J5svLJEPhLFTNf81cX.nlu4C1EP3YKJdFm6DatyFCEqci7U2cyHPC', 'tecnico', '0987654321', 'Calle Tecnico #456', NULL, '2025-04-10 05:47:02', '2025-06-10 01:18:01'),
(3, 'Cliente Uno', 'cliente@example.com', NULL, '$2y$12$LxFDn/Q2bZEMbx.cUZU15.9vdTYQk8ktuHYtEt1ehV8WMkdR2tvrK', 'cliente', '5555555555', 'Calle Cliente #789', NULL, '2025-04-10 05:47:02', '2025-04-10 05:47:02'),
(4, 'Jonathan Segura', 'segura@gmail.com', NULL, '$2y$12$8LCSpUaWF6cnTKgUldgQa.RhL2na5AE/1r5RUYDcBDsyHWoRcwWuy', 'tecnico', '1234567890', 'Dirección del tecnico 123', NULL, '2025-04-10 05:51:22', '2025-06-10 01:18:13'),
(6, 'yuly astrid', 'yuly@gmail.com', NULL, '$2y$12$jm62dtQIMc480mZLiq.Z1.Q33tP7409IrsDSTvD8nJjQjgdA.amsa', 'cliente', '3125747203', 'cr 812', NULL, '2025-04-10 07:03:26', '2025-04-10 07:03:26'),
(7, 'Diader andres', 'diader@gmail.com', NULL, '$2y$12$9QIopMWKiQEDr2C67qe6NeaBLj04pnlL/UKXR6UBLhRGviULKyQqm', 'tecnico', '3125747205', 'cr 81 57', NULL, '2025-04-11 04:39:30', '2025-04-11 04:39:30'),
(10, 'Juan Pablo Ballen Gonzalez', 'juanpoxc@gmail.com', NULL, '$2y$12$5eSXUWR94LTFMCmQryBx/Oxq8Y6jqGTYihA1XEGqGMxj4uZ2xC6VW', 'cliente', '3125767402', 'cr 81 j 57 c 20 sur', NULL, '2025-04-23 02:12:50', '2025-07-01 23:39:49'),
(11, 'Juan Pablo Ballen Gonzalez', 'juanpablo@gmail.com', NULL, '$2y$12$7ink6zuiS9ASwGpzUvTvf.bO05nKaeuRFIwum/UGKHgk6st6jeQ7C', 'tecnico', '3125767409', 'Sin direcciónn', NULL, '2025-04-23 02:13:35', '2025-07-02 00:04:50'),
(12, 'Camila Rodriguez', 'camila@gmail.com', NULL, '$2y$12$zquX0b8KA3kbMAbRwPzm9OiqrYRgQV.RZappHukkzRx2O.6nTPf86', 'cliente', '000-000-0000', 'Sin dirección', NULL, '2025-04-23 02:19:04', '2025-04-23 02:19:04'),
(13, 'Jonathan segura', 'jonathan@gmail.com', NULL, '$2y$12$msVSN359z2OVZcQabXcJ1.PXKGUXqSydTiDlUV2Yej.q.jW497sIm', 'cliente', '000-000-0000', 'Sin dirección', NULL, '2025-04-23 04:22:41', '2025-04-23 04:22:41'),
(14, 'camila electro', 'electro@gmail.com', NULL, '$2y$12$aaMfR5..Ij181VNd0355a.aD5eYl4L4F9msLugfncWEN4LwXgUrwy', 'cliente', '000-000-0000', 'Sin dirección', NULL, '2025-04-23 06:18:44', '2025-04-23 06:18:44'),
(15, 'yuly astrid ballen', 'astrid@gmail.com', NULL, '$2y$12$i/GWVSSY5ogkOrbtAkz4Q.V7MWrh2OHCqNjFRfBkmr/CtBlEWTTJe', 'cliente', '000-000-0000', 'Sin dirección', NULL, '2025-04-23 06:21:45', '2025-04-23 06:21:45'),
(17, 'diader andres rodriguez avila', 'andresrodrigueza55@gmail.com', NULL, '$2y$12$E8WvZfcP3Qok7u1icMBo2uypIJ/iSJDRAKgv7OQj6NEE2EnOkpok2', 'cliente', '0', '0', NULL, '2025-06-06 04:38:49', '2025-06-06 04:38:49'),
(18, 'Olga lucia', 'olga@gmail.com', NULL, '$2y$12$dzvrdbCFee0zE7ivedKyeOo0XsD9BU0zI.45GqqvjHaWuVVgKZM2.', 'cliente', '0', '0', NULL, '2025-06-10 01:40:15', '2025-06-10 01:40:15'),
(20, 'El suaves', 'suaves@gmail.com', NULL, '$2y$12$LqXixsqv7p4fxfUe9kuQWeptt6tF5nhfMDWODd9VChn.VWVKtQLra', 'cliente', '0', '0', NULL, '2025-06-10 04:41:29', '2025-06-10 04:41:29'),
(22, 'juanpis gonzalez', 'falanix50@gmail.com', NULL, '$2y$12$NDLY81MBd1NdpdW9Iln4g.qRagg7sBRxpJrF21tewOcIKEmBRIZQW', 'tecnico', '0', '0', NULL, '2025-06-13 20:39:32', '2025-06-13 20:39:32'),
(23, 'francis alexis escobar', 'francis@outlook.com', NULL, '$2y$12$qsN.wNuNb6g/aVSfEeZCLOQX03eokou3hXudjevCuXrv3Gre.T13u', 'tecnico', '3133193697', 'cr 80 57 c 29 sur', NULL, '2025-06-13 20:52:26', '2025-06-13 20:52:26'),
(25, 'celenita pepita', 'celina@gmail.com', NULL, '$2y$12$/I5f4sl/HNVtuYjv9bZLc./sgHdHJAeoB17CKbMoRPC6OSAl0nrHy', 'cliente', '0', '0', NULL, '2025-06-18 21:58:18', '2025-06-18 21:59:06'),
(26, 'Valentina gonzalez', 'valgonzalez@gmail.com', NULL, '$2y$12$TnXZcxIRyBuWujAcKP.p/OTov753OFMshjED5vizT7Sw4VUPu1Xiy', 'cliente', '0', '0', NULL, '2025-06-19 06:41:38', '2025-06-19 06:41:38'),
(27, 'Javier yara', 'javeryara@gmail.com', NULL, '$2y$12$Xv5CB3FmaED0ijnOzoERLOUcW3vCYdrPrwMdDtHSuV3xbxihhDkZS', 'cliente', '0', '0', NULL, '2025-06-27 05:23:25', '2025-06-27 05:23:25');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `appliances`
--
ALTER TABLE `appliances`
  ADD PRIMARY KEY (`id`),
  ADD KEY `appliances_user_id_foreign` (`user_id`);

--
-- Indices de la tabla `cache`
--
ALTER TABLE `cache`
  ADD PRIMARY KEY (`key`);

--
-- Indices de la tabla `cache_locks`
--
ALTER TABLE `cache_locks`
  ADD PRIMARY KEY (`key`);

--
-- Indices de la tabla `failed_jobs`
--
ALTER TABLE `failed_jobs`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `failed_jobs_uuid_unique` (`uuid`);

--
-- Indices de la tabla `jobs`
--
ALTER TABLE `jobs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jobs_queue_index` (`queue`);

--
-- Indices de la tabla `job_batches`
--
ALTER TABLE `job_batches`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD KEY `password_reset_tokens_email_index` (`email`);

--
-- Indices de la tabla `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`);

--
-- Indices de la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `servicios_cliente_id_foreign` (`cliente_id`),
  ADD KEY `servicios_tecnico_id_foreign` (`tecnico_id`);

--
-- Indices de la tabla `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessions_user_id_index` (`user_id`),
  ADD KEY `sessions_last_activity_index` (`last_activity`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `appliances`
--
ALTER TABLE `appliances`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `failed_jobs`
--
ALTER TABLE `failed_jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `jobs`
--
ALTER TABLE `jobs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `servicios`
--
ALTER TABLE `servicios`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `appliances`
--
ALTER TABLE `appliances`
  ADD CONSTRAINT `appliances_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `servicios`
--
ALTER TABLE `servicios`
  ADD CONSTRAINT `servicios_cliente_id_foreign` FOREIGN KEY (`cliente_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `servicios_tecnico_id_foreign` FOREIGN KEY (`tecnico_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
