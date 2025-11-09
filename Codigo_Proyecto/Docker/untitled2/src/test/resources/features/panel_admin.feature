#language:es
@admin
Característica: Panel de Administración
  Como administrador
  Quiero gestionar usuarios y servicios
  Para mantener el control del sistema

  Antecedentes:
    Dado que el usuario está autenticado como administrador
    Y está en el panel de administración

  @requiresAuth
  Escenario: Gestionar usuarios
    Cuando seleccione la sección "Gestión de Usuarios"
    Entonces debería ver la lista de usuarios registrados
    Y debería poder buscar usuarios por nombre o email
    Y debería poder cambiar el rol de un usuario

  @requiresAuth
  Escenario: Asignar técnico a servicio
    Cuando seleccione la sección "Servicios Pendientes"
    Y seleccione un servicio pendiente
    Y asigne un técnico al servicio
    Entonces el estado del servicio debería cambiar a "en_proceso"
    Y el técnico debería ser notificado

  @requiresAuth
  Escenario: Ver reportes de servicios
    Cuando seleccione la sección "Reportes"
    Y seleccione el rango de fechas
      | fechaInicio  | fechaFin    |
      | 2025-01-01   | 2025-12-31  |
    Entonces debería ver las estadísticas de servicios
    Y debería poder exportar el reporte