#language:es
@registro
Característica: Registro de usuarios en Electromovil
  Como visitante de Electromovil
  Quiero registrarme en el sistema
  Para poder acceder a los servicios de la aplicación

  Escenario: Registro exitoso de un nuevo usuario
    Dado que el visitante está en la página de registro
    Cuando ingrese los datos del nuevo usuario
      | nombre  | email           | telefono    | direccion      | password  | confirmarPassword |
      | Juan P  | juan@email.com  | 3001234567 | Calle 123 #45 | Pass123*  | Pass123*         |
    Y hace clic en el botón de registro
    Entonces debería ver un mensaje de registro exitoso
    Y debería ser redirigido a la página de inicio de sesión

  Escenario: Registro fallido por contraseñas que no coinciden
    Dado que el visitante está en la página de registro
    Cuando ingrese los datos con contraseñas diferentes
      | nombre  | email           | telefono    | direccion      | password  | confirmarPassword |
      | Juan P  | juan@email.com  | 3001234567 | Calle 123 #45 | Pass123*  | OtraPass123*      |
    Y hace clic en el botón de registro
    Entonces debería ver un mensaje de error indicando que las contraseñas no coinciden

  Escenario: Registro fallido por email ya registrado
    Dado que el visitante está en la página de registro
    Cuando ingrese un email que ya existe en el sistema
      | nombre  | email           | telefono    | direccion      | password  | confirmarPassword |
      | Juan P  | admin@admin.com | 3001234567 | Calle 123 #45 | Pass123*  | Pass123*         |
    Y hace clic en el botón de registro
    Entonces debería ver un mensaje indicando que el email ya está registrado