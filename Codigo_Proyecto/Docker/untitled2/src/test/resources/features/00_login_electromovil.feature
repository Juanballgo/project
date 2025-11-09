#language:es
@login
Característica: Login en la aplicación Electromovil
  Como usuario de Electromovil
  Quiero iniciar sesión en el sistema
  Para poder acceder a las funcionalidades de la aplicación

  Escenario: Inicio de sesión exitoso en Electromovil
    Dado que el usuario se encuentra en la página de inicio de sesión de Electromovil
    Cuando ingrese las credenciales correctas
      | email              | password    |
      | juanpoxc@gmail.com  | 147258369po   |
    Entonces debería ver la página de su rol

  Escenario: Inicio de sesión fallido en Electromovil
    Dado que el usuario se encuentra en la página de inicio de sesión de Electromovil
    Cuando ingrese credenciales incorrectas
      | email              | password    |
      | juanpoxc@gmail.com  | contraseñaIncorrecta |
    Entonces debería ver un mensaje de error de autenticación
