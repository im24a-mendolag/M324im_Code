// Exercise 3: checkout pipeline - the first stage of a commit pipeline.
// The repo was pushed to https://github.com/im24a-mendolag/trafficlight
pipeline {
    agent any
    tools { nodejs 'node22' }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/im24a-mendolag/trafficlight.git'
                // show what landed in the workspace
                sh 'ls -la'
            }
        }
    }
}
