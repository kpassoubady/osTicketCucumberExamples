@home-page @web-driver
Feature: Check the link and all the buttons present
  Once I land the right page
  I want to check the important links and buttons in that page

  @link-check
  Scenario: Verify the links in the osticket application home page
    When I'm on the home page of "http://osticket.kavinschool.com/"
    Then I verify that the link "Home" is present
    And I verify that the link "New Ticket" is present
    And I verify that the link "Ticket Status" is present

  @button-check
  Scenario: Verify the buttons in the osticket application home page
    When I'm on the home page of "http://osticket.kavinschool.com/"
    Then I verify that the Open New Ticket button is present
    And I verify that the Check Status button is present

