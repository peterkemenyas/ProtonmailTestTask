pipeline {
  agent any

  environment {
    PATH = "/opt/apache-maven/bin:$PATH"
  }
  stages {
      stage ("build and test"){
        steps {
            sh '''
             mvn clean install -Dspring.profiles.active="prod" -Dbrowser="firefox"
            '''

        }
      }
  }
}