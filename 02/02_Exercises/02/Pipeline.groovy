// Exercise 2: pipeline that proves the NodeJS tool is available on the controller.
// Prerequisite in Jenkins:
//   Manage Jenkins > Plugins > Available  -> install "NodeJS"
//   Manage Jenkins > Tools > NodeJS installations -> add one, Name = node22,
//                                                    "Install automatically" (NodeJS 22.x)
pipeline {
    agent any
    // 'tools' puts the configured NodeJS installation on the PATH of this build
    tools { nodejs 'node22' }
    stages {
        stage('Check Node.js') {
            steps {
                sh 'node --version'
                sh 'npm --version'
            }
        }
    }
}
