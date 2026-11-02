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
        stage('print path') {
            steps {
               sh 'echo $PATH'
            }
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t my-new-image:v1 .'
                echo 'Docker image built successfully'
            }
        }

        stage('Docker Deploy') {
            steps {
                bat '''
                docker rm -f my-new-container 2>NUL

                docker run -d ^
                    --name my-new-container ^
                    -p 8081:8081 ^
                    my-new-image:v1

                powershell -NoProfile -ExecutionPolicy Bypass -Command "$started = $false; for ($i=0; $i -lt 30; $i++) { Start-Sleep -Seconds 2; if (docker inspect -f '{{.State.Running}}' jenkins-demo-container 2>$null) { $started = $true; break } }; if (-not $started) { docker logs jenkins-demo-container; throw 'Docker container failed to start' }; if ((docker inspect -f '{{.State.Running}}' jenkins-demo-container) -ne 'true') { docker logs jenkins-demo-container; throw 'Docker container is not running' }"
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
