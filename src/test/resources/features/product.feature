Feature: Product Management for REST API

  Background:
    Given the API base URL is "http://localhost:8080/api/v1"
    And the product endpoint is "/products"

  Scenario: Add a new product with valid fields
    When I add a new product with the following details:
      | name        | description         | price | availableQuantity |
      | New Product | Product Description | 20.00 | 100               |
    Then the response status code should be 201
    And the response should contain new product id
    And the product details should be as follows:
      | name        | description         | price | availableQuantity | productStatus |
      | New Product | Product Description | 20.00 | 100               | available     |

  Scenario: Add a new product with negative price
    When I add a new product with the following details:
      | name             | description         | price | availableQuantity |
      | Negative Product | Invalid Description | -5.00 | 50                |
    Then the response status code should be 400
    And the response message should be "[\"Invalid Input: Price must be positive, provided -5.00\"]"

  Scenario: Product status changes when availableQuantity changes to 0
    Given a product with the following details exists:
      | name             | description     | price | availableQuantity |
      | Existing Product | Old Description | 30.00 | 10                |
    When I update the product with availableQuantity set to 0
    Then the response status code should be 201
    And the product details should be as follows:
      | name             | description     | price | availableQuantity | productStatus |
      | Existing Product | Old Description | 30.00 | 0                 | out of stock  |

  Scenario: Return product history of changes
    Given a product with the following details exists:
      | name            | description         | price | availableQuantity |
      | Historical Prod | Initial Description | 25.00 | 50                |
    When I update the product with the following details:
      | name            | description     | price | availableQuantity |
      | Historical Prod | New Description | 30.00 | 10                |
    Then the response status code should be 201
    And the product history should contain the following entries:
      | name            | description         | price | availableQuantity |
      | Historical Prod | New Description     | 30.00 | 10                |
      | Historical Prod | Initial Description | 25.00 | 50                |
