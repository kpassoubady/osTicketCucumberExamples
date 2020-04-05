@homepage, @webdriver
Feature: Check the title and verify all the verbiages
  In order for landing in to the right page
  I want to check the title of osticket application

  Scenario: Verify the title and veribiages of osticket application home page
    When I'm on the home page of "http://osticket.kavinschool.com/"
    Then I verify that the title is "KavinSchool:: Support Ticket System"
    And I verify that the header shows as "Welcome to the support center"
    And I verify the top right corner shows as "SUPPORT TICKET SYSTEM"
    And I verify the message in the middle shows with "In order to streamline support requests and better serve you, we utilize a support ticket system. Every support request is assigned a unique ticket number which you can use to track the progress and responses online. For your reference we provide complete archives and history of all your support requests. A valid email address is required."