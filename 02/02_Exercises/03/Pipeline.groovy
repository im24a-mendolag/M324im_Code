// Exercise 3: checkout pipeline - the first stage of a commit pipeline.
// Replace <user> with the GitHub account the traffic-light repo was pushed to.
pipeline {
    agent any
    tools { nodejs 'node22' }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/<user>/trafficlight.git'
                // show what landed in the workspace
                sh 'ls -la'
            }
        }
    }
}
