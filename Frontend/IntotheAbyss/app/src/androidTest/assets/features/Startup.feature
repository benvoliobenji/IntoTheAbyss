Feature:  Start with setting up our new application
  Scenario: App should launch when run
    Given I have a device with our app loaded
    When I select the app
    Then The app should start