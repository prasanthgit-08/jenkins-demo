
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
                bat '''
                if not exist C:\\jenkins-deploy mkdir C:\\jenkins-deploy
                powershell -NoProfile -Command "$jar = Get-ChildItem 'target' -Filter '*.jar' | Where-Object { $_.Name -notlike '*.original' } | Select-Object -First 1; if ($null -eq $jar) { throw 'JAR file not found' }; Copy-Item $jar.FullName 'C:\\jenkins-deploy\\jenkins-demo.jar' -Force"
                '''
                echo 'Deployment artifact copied successfully'
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
