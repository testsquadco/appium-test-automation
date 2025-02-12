Feature: Login Functionality

  @smoke @regression
  Scenario: User can login with valid credentials
    Given the app is launched
    When user enters username "testuser"
    And user enters password "password123"
    And user taps on login button
    Then user should be logged in successfully
    And home screen should be displayed

  @regression
  Scenario Outline: User cannot login with invalid credentials
    Given the app is launched
    When user enters username "<username>"
    And user enters password "<password>"
    And user taps on login button
    Then error message "<error>" should be displayed

    Examples:
      | username | password    | error                        |
      | invalid  | password123 | Invalid username or password |
      | testuser | wrong      | Invalid username or password |
      |          | password123 | Username is required        |
      | testuser |            | Password is required        | 