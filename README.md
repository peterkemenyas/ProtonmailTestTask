# ProtonMail Automated Testing for "Folder/Labels Settings"

This project is for writing automated UI tests against any ProtonMail environment using Selenium with Java. 
The purpose of this project is to complete the technical task provided during interview process.  
# Concepts included
* Page Object pattern
* Fluent interface
* Common web page interaction methods
* Externalised test configuration
* Stateless test case

## Getting Started

After cloning it, you can run the entire test suite or specific test case against the given environment.

### Tools
* [Maven](https://maven.apache.org/)
* [TestNG](https://testng.org/doc/)
* [Selenium WebDriver](https://www.selenium.dev/)
* [Spring](https://spring.io/)
* [IntelliJ IDEA CE](https://www.jetbrains.com/idea/)
### Prerequisites
What things you need to install the software and how to install them
```
Java 8+
Maven 3.6+
Firefox 75+
```
## Running the tests from command line
The automated tests can be run against different environments, using different browsers. 
The command to run all the tests against production using firefox browser is the following:
```
mvn test -Dspring.profiles.active="prod" -Dbrowser="firefox"
```
The command to run specific test (for example "editFolder")
```
mvn test -Dspring.profiles.active="prod" -Dbrowser="firefox" -Dtest=TestLabelsFolders#editFolder
```
Template command:
```
mvn test -Dspring.profiles.active="<ENVIRONMENT>" -Dbrowser=<BROWSER>
```
Where the Environment can be the following:
- **prod**: for testing Production environment with production test data
- **int**: for testing Integration environment with integration test data
- **local**: for testing Local environment with local test data

Where the Browser can be the following:
- **chrome**: for running automated test using Google Chrome
- **firefox**: for running automated test using Firefox
- **ie**: for running automated test using Internet Explorer

Note: Chrome has issues during execution. I did not try with IE. 
I added them as options for future improvements.   
## Running the tests from command line
Test cases can be executed from IDE.
* In "Preferences" set the Maven settings
* In "Run/Debug Configurations -> Templates -> TestNG" set  "VM Options"  to 
```
-ea -Dspring.profiles.active="prod" -Dbrowser="firefox"
```
* Simply click on "Run test" icon next to required test method or test class. 
### Test Design
As I did not have access to original Software Requirements Specification document I was using exploratory testing approach. 

Functionality/Area | Coverage
------------ | -------------
Add folder | addFolder()
Edit folder | editFolder()
Delete folder | deleteFolder()
Add label | addLabel()
Edit label | editLabel()
Delete label | deleteLabel()
Validation - Same folder/label name | folderSameNameValidation(), labelSameNameValidation() 
Validation - Folder/label name length | addFolderMaxNameValid(), addLabelMaxNameValid(), addFolderMaxNameInvalid(), addLabelMaxNameInvalid()
Validation - Free account and folder limit reached | folderLimitReached()
Validation - Folder/label empty name not allowed | editFolderEmptyNameValidation(), editLabelEmptyNameValidation()
Edit folder - notification on/off | Not covered
Page layout | Not covered
Page links | Not covered
Validation - Folder/label special characters (if exists) | Not covered 

During test automation I found an issue which could be a defect. 

Title: "User session terminates after 2 refreshes"

Steps to reproduce: 
1. Login to "Protonmail Beta" using test automation 
2. Do refresh twice 

Actual Result: User is logged out

Expected Result: This needs to be clarified with Business Analyst or Product Owner. 

Note: I could not reproduce it manually as browser does not allow to click so fast the Refresh button. To reproduce the issue you can execute testToTriggerLogOutIssue() test method.

## CI Pipeline
I am using MacOS so all commands will be for this OS.
* Install Jenkins
```
brew install jenkins-lts
```
* Start Jenkins
```
brew services start jenkins-lts
```
* Open Jenkins in Browser (http://localhost:8080/), set 1st admin password and install suggested plugins
* Create admin user 
* After create a new job. Select Pipeline, enter name "ProtonMailTest" and click OK.
* In Pipeline section select "Pipeline script from SCM". SCM = "Git" 
* Repository URL = https://github.com/peterkemenyas/ProtonmailTestTask.git and click Save.
* 
 