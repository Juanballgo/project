#language:es
@perfil
Característica: Gestión del perfil de usuario
  Como usuario registrado
  Quiero gestionar mi perfil
  Para mantener mi información actualizada

  Antecedentes:
    Dado que el usuario está autenticado
    Y está en la página de perfil

  @requiresAuth
  Escenario: Actualizar información personal
    Cuando edite su información personal
      | nombre      | telefono    | direccion        |
      | Juan Pablo  | 3009876543  | Nueva Calle #123 |
    Y guarde los cambios
    Entonces debería ver un mensaje de actualización exitosa
    Y la información debería estar actualizada

  @requiresAuth
  Escenario: Cambiar contraseña
    Cuando seleccione cambiar contraseña
    Y complete el formulario de cambio de contraseña
      | actualPassword | nuevaPassword | confirmarPassword |
      | Pass123*      | NewPass123*   | NewPass123*      |
    Entonces debería ver un mensaje de cambio de contraseña exitoso

  @requiresAuth
  Escenario: Visualizar electrodomésticos registrados
    Cuando seleccione la sección "Mis Electrodomésticos"
    Entonces debería ver una lista de sus electrodomésticos registrados
    Y debería ver la opción de agregar nuevo electrodoméstico