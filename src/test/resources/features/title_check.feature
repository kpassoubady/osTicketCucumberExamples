@home-page @web-driver
Feature: Check the title
  In order for landing in to the right page
  I want to check the title of osticket application

  @title-check
  Scenario: Verify the title of osticket application
    When I'm on the home page of "http://osticket.kavinschool.com/"
    Then I verify that the title is "KavinSchool:: Support Ticket System"
