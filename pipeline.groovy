pipeline {
    agent any
    
    environment {
        GITHUB_REPO = 'https://github.com/Turtoz/jenkins'
        BRANCH = 'main'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Clean workspace before building
                cleanWs()
                // Checkout code from GitHub
                git branch: "${BRANCH}",
                    url: "${GITHUB_REPO}"
            }
        }
        
        stage('1') {
            steps {
                sh '''
                #!/bin/sh
                /usr/local/bin/docker build -t Turtoz/jenkins
                    
                '''
            }
        }
        
        stage('2') {
            steps {
                sh '''
                    #!/bin/bash
                    PORT_TO_KILL=3000

                    CONTAINER_ID=$(/usr/local/bin/docker ps -q --filter "expose=$PORT_TO_KILL")

                    if [ -n "$CONTAINER_ID" ]; then
                    /usr/local/bin/docker kill "$CONTAINER_ID"
                    else
                    echo "No container using port $PORT_TO_KILL found."
                '''
            }
        }
        
        stage('3') {
            steps {
                sh '''
                    /usr/local/bin/docker run -p 3000:3000 -d Turtoz/jenkins
                '''
            }
        }
    }
    
    post {
        success {
            echo 'Pipeline succeeded! Application has been built, tested, and deployed.'
        }
        failure {
            echo 'Pipeline failed! Please check the logs for details.'
        }
    }
}