Feature: Login and Report Progress

  @complete-flow
  Scenario: Complete technician workflow from login to report
    Given that "Andres" is on the login page
    When he logs in with his valid credentials
    Then he must access his technical profile
    When he reports progress for an assigned service
    Then the report should be sent successfully

  @profile-update
  Scenario: Update technician profile
    Given that "Andres" is on the login page
    When he logs in with his valid credentials
    Then he must access his technical profile
    When he updates his technical profile with the following data
    Then the profile should be updated successfully