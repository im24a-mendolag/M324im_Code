// Exercise 4: the complete commit pipeline for an interpreted language.
// Checkout -> Install -> Test  (no Compile stage: Node.js is not compiled)
pipeline {
    agent any
    tools { nodejs 'node22' }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/<user>/trafficlight.git'
            }
        }
        stage('Install') {
            steps {
                // installs the dependencies from package.json (jest)
                sh 'npm install'
            }
        }
        stage('Test') {
            steps {
                // runs the jest suite via the "test" script in package.json
                sh 'npm test'
            }
        }
    }
}
