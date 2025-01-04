pipeline {
    agent any
    tools {
        maven 'sonarmaven' // Ensure this matches the Maven tool configuration in Jenkins
    }
    environment {
        SONAR_TOKEN = credentials('sonar-token') // SonarQube token credentials ID
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm // Pulls the code from the repository
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean package -X' // Build the project with debug logs
            }
        }
        stage('Run Tests') {
            steps {
                bat 'mvn verify -X' // Run tests and generate coverage report with debug logs
            }
        }
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') { // Use SonarQube configuration in Jenkins
                    bat """
                        mvn sonar:sonar 
                        -Dsonar.projectKey=sonar-maven2 
                        -Dsonar.sources=src/main/java 
                        -Dsonar.tests=src/test/java 
                        -Dsonar.junit.reportPaths=target/surefire-reports 
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml 
                        -Dsonar.host.url=http://localhost:9000 
                        -Dsonar.token=%SONAR_TOKEN% 
                        -X
                    """
                }
            }
        }
    }
    post {
        always {
            echo 'Archiving artifacts for debugging...'
            archiveArtifacts artifacts: '**/target/site/jacoco/*.xml', allowEmptyArchive: true
            archiveArtifacts artifacts: '**/target/surefire-reports/*.xml', allowEmptyArchive: true
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Checking test results...'
            junit 'target/surefire-reports/*.xml' // Archive test results
        }
    }
}
