@home-page @web-driver
Feature: Check the opening of a new ticket in osticket application
  I want to open a new ticket using osticket application
  I want to check whether the ticket successfully submitted

  Background: Verify the title of osticket application
    When I'm on the home page of "http://osticket.kavinschool.com/"
    Then I verify that the title is "KavinSchool:: Support Ticket System"

  @form-check
  Scenario Outline: Verify new ticket creation in osticket application
    When I clicked on the Open New Ticket button
    Then I will wait for "Please fill in the form below to open a new ticket." text to shows
    When I checked the Full Name "Full Name:" label exist
    Then I type first name "<firstName>" value
    When I checked the Email Address "Email Address:" label exist
    Then I type email address "<emailAddress>" value
    When I checked the Telephone "Telephone:" and Extension "Ext" label exist
    Then I type telephone "<telephone>" and "<Ext>" value
    When I checked the Help Topic "Help Topic:" label exist
    Then I type select a help topic "<helpTopic>" from dropdown
    When I checked the subject "Subject:" label exist
    Then I type a subject "<subject>" value
    When I checked the message "Message:" label exist
    Then I type a message "<message>" value
    And  I click on the Submit Ticket button
    When I waited for a new created message shows up with "Support ticket request created"
    And I verify that the message contains first name "<firstName>" and email address "<emailAddress>"

    Examples:
      | firstName  | emailAddress          |telephone       | Ext    | helpTopic |subject               |message                         |
      | Kangs Pass | kangs@kavinschool.com | 1-510-991-7591 |  1234  | Education | Testing a new ticket | Testing a ticket with a message|
#      | Lovely Dad | kangs@kavinschool.com | 1-510-991-7591 |  1234  | Education | Testing a new ticket | Testing a ticket with a message|
