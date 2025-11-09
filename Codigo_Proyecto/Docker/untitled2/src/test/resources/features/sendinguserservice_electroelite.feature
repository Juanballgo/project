#language: es
#author: Juan Pablo Ballen

Característica: Envío de formulario para servicio en la página ElectroElite
  Como usuario de ElectroElite
  Quiero enviar una solicitud de servicio técnico
  Para poder recibir ayuda con la reparación de mi lavadora o nevera

  @formulario_servicio
  Escenario: Envío exitoso del formulario de solicitud de servicio
    Dado que el usuario se encuentra en la sección "Solicitar Servicio" de la página ElectroElite
    Cuando ingrese los datos válidos en el formulario de servicio
      | nombre        | teléfono     | dirección                 | electrodoméstico | descripción                       |
      | Carlos López  | 3004567890   | Calle 45 #10-22 Bogotá    | Lavadora         | No exprime y hace ruido fuerte    |
    Y haga clic en el botón "Enviar solicitud"
    Entonces se debe mostrar un mensaje confirmando el envío exitoso
    Y el sistema debe almacenar la solicitud para seguimiento