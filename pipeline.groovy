pipeline {
    agent any
    
    environment {
        GITHUB_REPO = 'https://github.com/your-username/your-repo.git'
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
        
        stage('Build') {
            steps {
                // Add your build commands here
                sh '''
                    echo "Building the application..."
                    # Add your build commands (e.g., mvn clean install, npm install, etc.)
                '''
            }
        }
        
        stage('Test') {
            steps {
                sh '''
                    echo "Running tests..."
                    # Add your test commands
                '''
            }
        }
        
        stage('Deploy') {
            steps {
                sh '''
                    echo "Deploying application..."
                    # Add your deployment commands
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