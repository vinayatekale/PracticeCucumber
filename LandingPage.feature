Feature: Accessing the Guardian Website
  This feature deals with opening the guardian Website

  Scenario: Accessing the Guardian website
    Given I navigate to the Guardian website
    When I Accept the cookies message
    Then I should see the Guardian website default page

