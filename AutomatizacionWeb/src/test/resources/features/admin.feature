Feature: Admin area - create resources
  As an administrator I want to perform critical actions so the automation covers user and service creation

  @admin-create-user
  Scenario: Create a new user from admin UI
    Given that "Admin" is on the login page
    When he logs in with credentials "admin@electroelite.com" "password"
    And he opens users management
    When he creates a new user via the UI
    Then the new user should appear in the users list

  @admin-create-service
  Scenario: Create a new service from admin UI
    Given that "Admin" is on the login page
    When he logs in with credentials "admin@electroelite.com" "password"
    And he opens services management
    When he creates a new service via the UI
    Then the new service should appear in the services list

  @admin-change-service-status
  Scenario: Create a service and change its status to en_proceso
    Given that "Admin" is on the login page
    When he logs in with credentials "admin@electroelite.com" "password"
    And he opens services management
    When he creates a new service via the UI
    And he changes the created service's status to "en_proceso"
    Then the service should show status "en_proceso" in the services list

  @admin-change-service-status
  Scenario: Create a service and change its status to "completado"
    Given that "Admin" is on the login page
    When he logs in with credentials "admin@electroelite.com" "password"
    And he opens services management
    When he creates a new service via the UI
    And he changes the created service's status to "completado"
    Then the service should show status "completado" in the services list

  @admin-change-service-status
  Scenario: Create a service and change its status to "cancelado"
      @admin-change-service-status
      Scenario: Create a service and change its status to "pendiente"
        Given that "Admin" is on the login page
        When he logs in with credentials "admin@electroelite.com" "password"
        And he opens services management
        When he creates a new service via the UI
        And he changes the created service's status to "pendiente"
        Then the service should show status "pendiente" in the services list

    Given that "Admin" is on the login page
    When he logs in with credentials "admin@electroelite.com" "password"
    And he opens services management
    When he creates a new service via the UI
    And he changes the created service's status to "cancelado"
    Then the service should show status "cancelado" in the services list
