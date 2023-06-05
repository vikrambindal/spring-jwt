Feature: Greet User

  Background:
    Given a new user with details
      """
        {
          'firstName': 'Clark',
          'lastName': 'Kent',
          'email': 'clark.kent@justice-league.com',
          'password': 'martha',
          'role': 'USER'
        }
      """
    And a user registers in the application
    And a user is registered successfully with response
      | property     | matcher  | expected |
      | $.status     | is       | OK       |
      | $.body       | contains | token    |
      | $.body.token | notNull  |          |

  Scenario: A user with valid token is able to access to the application
    Given a user with login details
      """
        {
          'email': 'clark.kent@justice-league.com',
          'password': 'martha'
        }
      """
    And a user generates a token
    And token is generated with response
      | property     | matcher  | expected |
      | $.status     | is       | OK       |
      | $.body       | contains | token    |
      | $.body.token | notNull  |          |
    When user invokes greeting application
    Then user is greeted with their details in response
      | property   | matcher  | expected           |
      | $.status   | is       | OK                 |
      | $.body     | contains | msg                |
      | $.body.msg | is       | Welcome Clark Kent |
