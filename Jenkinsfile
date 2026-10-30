
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

                powershell -NoProfile -ExecutionPolicy Bypass -Command "$jar = Get-ChildItem 'target' -Filter '*.jar' | Where-Object { $_.Name -notlike '*.original' } | Select-Object -First 1; if ($null -eq $jar) { throw 'JAR file not found' }; $pids = Get-NetTCPConnection -LocalPort 8081 -State Listen -ErrorAction SilentlyContinue | Select-Object -ExpandProperty OwningProcess -Unique; foreach ($id in $pids) { Stop-Process -Id $id -Force }; Start-Sleep -Seconds 3; Copy-Item $jar.FullName 'C:\\jenkins-deploy\\jenkins-demo.jar' -Force; $env:JENKINS_NODE_COOKIE = 'dontKillMe'; $proc = Start-Process -FilePath 'C:\\Program Files\\Java\\jdk-21.0.12.1\\bin\\java.exe' -ArgumentList '-jar','C:\\jenkins-deploy\\jenkins-demo.jar','--server.port=8081' -WorkingDirectory 'C:\\jenkins-deploy' -RedirectStandardOutput 'C:\\jenkins-deploy\\app.log' -RedirectStandardError 'C:\\jenkins-deploy\\error.log' -PassThru; $started = $false; for ($i=0; $i -lt 30; $i++) { Start-Sleep -Seconds 2; if ($proc.HasExited) { Get-Content 'C:\\jenkins-deploy\\error.log'; throw 'Spring Boot application stopped unexpectedly' }; if (Get-NetTCPConnection -LocalPort 8081 -State Listen -ErrorAction SilentlyContinue) { $started = $true; break } }; if (-not $started) { Get-Content 'C:\\jenkins-deploy\\app.log' -Tail 40; throw 'Application did not start on port 8081' }"
                '''

                echo 'Application deployed and startup verified successfully'
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
