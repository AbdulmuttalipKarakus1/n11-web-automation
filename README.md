# N11 Automation Test Project

This project automates a scenario on [n11.com](https://www.n11.com/magazalar) using:

* [Java](https://www.oracle.com/java/technologies/downloads/) - The software language
* [Selenium](http://www.seleniumhq.org/) - The web framework to automate browsers
* [Maven](https://maven.apache.org/) - Dependency Management
* [Cucumber](https://cucumber.io/) - Behavior Driven Development (BDD) library
* [Allure reports](http://allure.qatools.ru/) - Reporting
* [TestNG](https://testng.org) - Testing framework

## Version Control 
* [Github](https://github.com/AbdulmuttalipKarakus1/n11-web-automation) - The repo of project


## Setup for mac

* Install Java and Maven (MacOS):
~~~~
brew install openjdk
~~~~

~~~~
brew install maven
~~~~

~~~~
brew install allure
~~~~

* Clone repo and run:

~~~~
mvn clean test
~~~~
   

* Generate Allure Report:

~~~~
allure serve allure-results
~~~~
   


## Scenario

- Open N11 store page
- Filter stores starting with "S"
- Click a random store
- Verify the store page
---
- Search "iPhone", add first and last product on first page
- Verify products on the cart
---
- Search "telefon", 
- Filter by second brand
- Sort by "number of reviews"
- Filter by "free shipping"
- Verify products on the list according to filter and sort
