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
