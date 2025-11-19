Feature: End-user (Usuario) flows

  @user-profile
  Scenario: Update user profile from profile modal
    Given that "Usuario" is on the login page
    When he logs in with credentials "juanpoxc@gmail.com" "147258369po"
    Then he opens the profile modal
    When he updates his profile with new valid data
    Then the profile update flow finishes successfully

  @appliance-service
  Scenario: Create service using the public service form
    Given that "Usuario" is on the login page
    When he logs in with credentials "juanpoxc@gmail.com" "147258369po"
    Then he fills and submits the public service request form
    Then the service should be created successfully

  @appliance-create
  Scenario: Add a new appliance from the appliances section
    Given that "Usuario" is on the login page
    When he logs in with credentials "juanpoxc@gmail.com" "147258369po"
    Then he opens appliances section
    When he adds a new appliance with valid data
    Then the appliance should be listed in the appliances grid
