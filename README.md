# ProtonMail Automated Testing for "Folder/Labels Settings"

This project is for writing automated UI tests against any ProtonMail environment using Selenium with java.

## Getting Started

You can clone the project from here: <GITHUB_REPO_URL>. After cloning it you can run the entire test suite against the given environment.

### Prerequisites

What things you need to install the software and how to install them

```
Java 8+
Maven 3.6+
Firefox 75+
```

## Running the tests

The automated tests can be run against different environments, using different browsers. The command to run all the tests is the following:
```
mvn test -Dspring.profiles.active="<ENVIRONMENT>" -D????=<BROWSER>
```
Where the Environment can be the following:
- **prod**: for testing Production environment with production test data
- **int**: for testing Integration environment with integration test data
- **local**: for testing Local environment with local test data

Where the Browser can be the following:
- **chrome**: for running automated test using Google Chrome
- **firefox**: for running automated test using Firefox
- **ie**: for running automated test using Internet Explorer



### Break down into end to end tests

Explain what these tests test and why

```
Give an example
```

### And coding style tests

Explain what these tests test and why

```
Give an example
```