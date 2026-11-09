
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
                bat '''
                set "PATH=C:/Windows/System32/WindowsPowerShell/v1.0;%PATH%"
                call mvnw.cmd clean verify
                '''
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar',
                                 fingerprint: true
            }
        }

        stage('Print Path') {
            steps {
                bat 'echo %PATH%'
            }
        }

       stage('Docker Compose Deploy') {
            steps {
                bat '''
                set "PATH=C:/Users/Prasanth/AppData/Local/Programs/DockerDesktop/resources/bin;%PATH%"
                docker compose up -d --build
                '''
                echo 'Docker Compose deployment completed successfully'
            }
        }

        stage('Docker Compose Verification') {
            steps {
                bat '''
                set "PATH=C:/Users/Prasanth/AppData/Local/Programs/DockerDesktop/resources/bin;%PATH%"

                docker compose ps

                docker compose logs --tail=50
                '''
                echo 'Docker Compose verification completed successfully'
            }
        }
    }

    post {
        success {
            echo 'CI/CD pipeline completed successfully!'
        }

        failure {
            echo 'Pipeline failed. Check the Console Output.'
        }
    }
}
