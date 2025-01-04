pipeline {
    agent any
    tools {
        maven 'sonarmaven' // Ensure this matches the Maven configuration in Jenkins
    }
    environment {
        SONAR_TOKEN = credentials('sonar-token') // Replace with your credentials ID for the SonarQube token
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm // Checks out the source code from GitHub
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package' // Builds the Maven project
            }
        }
        stage('Run Tests') {
            steps {
                bat 'mvn verify' // Executes tests and generates the JaCoCo coverage report
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') { // Ensure this matches your SonarQube configuration
                    bat """
                        mvn sonar:sonar 
                        -Dsonar.projectKey=sonar-maven2 
                        -Dsonar.sources=src/main/java 
                        -Dsonar.tests=src/test/java 
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml 
                        -Dsonar.host.url=http://localhost:9000 
                        -Dsonar.token=%SONAR_TOKEN%
                    """
                }
            }
        }
    }
    post {
        success {
            echo 'Pipeline completed successfully.'
            archiveArtifacts allowEmptyArchive: true, artifacts: '**/target/*.jar', followSymlinks: false
        }
        failure {
            echo 'Pipeline failed.'
            junit '**/target/surefire-reports/*.xml' // Archive test results if available
        }
    }
}
