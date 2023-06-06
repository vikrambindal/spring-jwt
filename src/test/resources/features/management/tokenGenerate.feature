Feature: Generate Token

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

  Scenario: An existing registered user is able to generate token sucessfully
    Given a user with login details
      """
        {
          'email': 'clark.kent@justice-league.com',
          'password': 'martha'
        }
      """
    When a user generates a token
    Then token is generated with response
      | property     | matcher  | expected |
      | $.status     | is       | OK       |
      | $.body       | contains | token    |
      | $.body.token | notNull  |          |
    Then generated token contains claims
      | property | matcher | expected                      |
      | $.sub    | is      | clark.kent@justice-league.com |
      | $.aud    | is      | application                   |
      | $.roles  | is      | USER                          |

  Scenario: An invalid user fails to generate token
    Given a user with login details
      """
        {
          'email': 'iron.man@marvel-universe.com',
          'password': 'tonyStark'
        }
      """
    When a user generates a token
    Then token is generated with response
      | property | matcher      | expected     |
      | $.status | is           | UNAUTHORIZED |
      | $.body   | isNotDefined |              |