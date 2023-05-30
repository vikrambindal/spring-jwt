Feature: Register User

  Scenario: A new user with valid details is created successfully with a jwt token
    Given a new user with details
      """
        {
          'firstName': 'Clark',
          'lastName': 'Kent',
          'email': 'clark.kent@justice-league.com',
          'password': 'martha'
        }
      """
    When a user registers in the application
    Then a user is registered successfully with response
      | property     | matcher  | expected |
      | $.status     | is       | OK       |
      | $.body       | contains | token    |
      | $.body.token | notNull  |          |