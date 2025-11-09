#language:es
@servicios
Característica: Gestión de servicios técnicos
  Como cliente de Electromovil
  Quiero solicitar y gestionar servicios técnicos
  Para reparar mis electrodomésticos

  Antecedentes:
    Dado que el usuario está autenticado como cliente
    Y está en la página de servicios

  @requiresAuth
  Escenario: Solicitar un nuevo servicio técnico
    Cuando seleccione "Solicitar nuevo servicio"
    Y complete el formulario de servicio
      | tipoEquipo | marca    | modelo  | descripcionProblema               |
      | Lavadora   | Samsung  | WF1000  | No enciende después de un apagón |
    Y envíe la solicitud
    Entonces debería ver un mensaje de confirmación
    Y el servicio debería aparecer en la lista con estado "pendiente"

  @requiresAuth
  Escenario: Ver historial de servicios
    Cuando seleccione "Mis servicios"
    Entonces debería ver una lista de todos sus servicios solicitados
    Y cada servicio debería mostrar su estado actual

  @requiresAuth
  Escenario: Cancelar un servicio pendiente
    Dado que tiene un servicio en estado "pendiente"
    Cuando seleccione la opción de cancelar servicio
    Y confirme la cancelación
    Entonces el estado del servicio debería cambiar a "cancelado"
    Y debería ver un mensaje de cancelación exitosa