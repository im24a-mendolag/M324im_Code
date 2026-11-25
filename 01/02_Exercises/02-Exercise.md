### Exercise 2: Multi-staged pipeline
####  Prerequisite
1. Docker container environment (i.e. actual version of Docker-Desktop) is mandatory.
2. You need a running Jenkins controller (master)

####  Tasks
1. For experiencing multi-stage and -step pipeline add a new item in Jenkins

New Item -> name `multistaged-pipeline` -> type `Pipeline` -> OK.
In the section *Pipeline* choose "Pipeline script" and paste the code below.

2. You can use as blueprint the pipeline code below:

[<img src="img/04-multistagedpipeline.png" width="250"/>](img/04-multistagedpipeline.png)

```groovy
pipeline {
    agent any
    stages {
        stage('1st Stage'){
            steps {
                echo 'Step 1. Hello World'
            }
        }
        stage('2nd Stage'){
            steps {
                echo 'Step 2. 2nd Hello World'
                echo 'Step 3. 3rd Hello World'
            }
        }
    }
}
```
(also stored as `Jenkinsfile.multistage`)

3. Build and see what result issues Jenkins on the console

*Build Now* -> Console Output of build #1:

```
Started by user admin
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins in /var/jenkins_home/workspace/multistaged-pipeline
[Pipeline] {
[Pipeline] stage
[Pipeline] { (1st Stage)
[Pipeline] echo
Step 1. Hello World
[Pipeline] }
[Pipeline] // stage
[Pipeline] stage
[Pipeline] { (2nd Stage)
[Pipeline] echo
Step 2. 2nd Hello World
[Pipeline] echo
Step 3. 3rd Hello World
[Pipeline] }
[Pipeline] // stage
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
```

Result: Jenkins executes the stages **sequentially** in the declared order, and the
steps **inside** a stage sequentially too. The *Stage View* on the job page shows one
column per stage with its own duration, so you can see where time is spent and which
stage fails. If a step fails, the whole run stops there and the following stages are
skipped — that is the point of splitting a build into stages.
