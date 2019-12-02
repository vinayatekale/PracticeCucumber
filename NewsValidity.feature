Feature: Validating the news displayed
  This feature deals with validating the displayed news

  #Valid News Verification
  Scenario: Validating the displayed news
    Given I navigate to the Guardian website
    And I Accept the cookies message
    When I read the first news displayed
    And I read the search result from google for same topic
    Then I should see google search displaying relevant information