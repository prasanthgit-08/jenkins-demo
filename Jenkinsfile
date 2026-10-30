
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo 'Source code checked out successfully'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'call mvnw.cmd clean verify'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }

        stage('Deploy') {
            steps {
                bat 'powershell -NoProfile -ExecutionPolicy Bypass -File deploy.ps1'
                echo 'Application deployed successfully'
            }
        }
    }

    post {
        success {
            echo 'CI/CD pipeline completed successfully!'
            echo 'Application URL: http://localhost:8081'
        }

        failure {
            echo 'Pipeline failed. Check the Console Output.'
        }
    }
}
