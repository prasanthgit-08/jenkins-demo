
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

        stage('Docker Image Build') {
            steps {
                bat '''
                set "PATH=C:/Users/Prasanth/AppData/Local/Programs/DockerDesktop/resources/bin;%PATH%"
                docker build -t my-new-image:v1 .
                '''
                echo 'Docker image built successfully'
            }
        }

        stage('Docker Container Remove') {
            steps {
                bat '''
                set "PATH=C:/Users/Prasanth/AppData/Local/Programs/DockerDesktop/resources/bin;%PATH%"
                docker rm -f my-new-container || exit /b 0
                '''
            }
        }

        stage('Docker Container Run') {
            steps {
                bat '''
                set "PATH=C:/Users/Prasanth/AppData/Local/Programs/DockerDesktop/resources/bin;%PATH%"
                docker run -d -p 8083:8081 --name my-new-container my-new-image:v1
                '''
                echo 'Docker container started successfully'
            }
        }

        stage('Docker Deploy') {
            steps {
                bat '''
                set "PATH=C:/Users/Prasanth/AppData/Local/Programs/DockerDesktop/resources/bin;%PATH%"
                docker ps
                docker logs my-new-container
                '''
                echo 'Docker container deployed successfully'
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
