Feature: Gestión de servicios y electrodomésticos por el usuario (cliente)

  Background:
    Given que el usuario cliente está logueado correctamente

  Scenario: Solicitar un servicio técnico
    When el usuario completa el formulario de solicitud de servicio
    Then debería ver el nuevo servicio en la lista de servicios

  Scenario: Añadir un nuevo electrodoméstico
    When el usuario completa el formulario para añadir un electrodoméstico
    Then debería ver el nuevo electrodoméstico en la lista
