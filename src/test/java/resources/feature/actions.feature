Feature: Testing CRUD operation
  @GetAllData
  Scenario:Sending GET request
    When send GET request
    Then I verify status code 200

  @CRUD
  Scenario:verify if new user can be created
    Given I make POST request with following data
    |name|email|gender|status|
    |john|abc123@gmail.com|male|active|
    When make response response is 200
    And fetch the data and check
    And check the id
    And I update data with following details
    |name|email|status|
    |max |xvb123@gmail.com|active|
    And verify the status code
    Then delete the data by id
    And verifyit by fetching again by id


  @negative @GetAllData
  Scenario:Trying to fetch data without authentification
    When Try to access specificdata data without authentification
    Then I should receive forbidden message

  @negative @PostDataWithSomeMissingColumns
    Scenario Outline:Trying to POST data with one column missing
    When POST this data and try to create user with only"<name>","<email>","<status>" data
    Then received message regarding the field which is entry and data will not be created
    Examples:
      |name|email|status|
      |max    |xcv123@gmail.com|active|