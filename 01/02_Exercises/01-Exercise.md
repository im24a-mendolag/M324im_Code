### Exercise 1: Installing and start Jenkins as container

### Prerequisite

Docker container environment (i.e. actual version of Docker-Desktop) is mandatory.

### Tasks

1. Login with SSH and port mapping on your AWS EC2 instance.
   Example:

```bash
# the private key must only be readable by the owner, otherwise ssh refuses it
chmod 400 ~/.ssh/M324-mendolag.pem
# -L maps the remote Jenkins ports to the same ports on the local machine,
# so Jenkins can be opened at http://localhost:8080 without a public inbound rule
ssh -i ~/.ssh/M324-mendolag.pem \
    -L 8080:localhost:8080 \
    -L 50000:localhost:50000 \
    ubuntu@<ec2-public-dns>
```

2. Check if the Jenkins image (from Docker Hub registry) is already pulled for you. If 
not then do it by yourself.

```bash
# check first whether the image is already there ...
docker image ls | grep jenkins
# ... and only pull it if the list above stays empty
docker pull jenkins/jenkins:lts
```

3. Create a volume directory to keep (persist) the Jenkins data on your host

```bash
mkdir -p $HOME/jenkins_home
```

4. Run Jenkins as a container and give it a proper name to the container:

```bash
docker run -d --name jenkins --restart unless-stopped \
  -p 8080:8080 \
  -p 50000:50000 \
  -v $HOME/jenkins_home:/var/jenkins_home \
  jenkins/jenkins:lts
```

The image tag must be the same one pulled in step 2 (`:lts`), otherwise Docker
downloads `jenkins/jenkins:latest` a second time. Port 50000 is the port the
build agents connect to.

5. Jenkins will ask for the administrator password. It can be found in the Jenkins logs:

```bash
docker logs jenkins
```

[<img src="img/01.png" width="250"/>](img/01.png)

If you don't see the complete logs than wait some 30-60 seconds until Jenkins
has completely started.

6. After accepting the initial password, Jenkins asks whether to install the suggested plugins, which are adjusted for the most common use cases. As the first Jenkins installation, it's reasonable to let Jenkins install all the recommended plugins.
7. After the plugin installation, Jenkins asks you to set up a username, password, and other basic information. If you skip it, the token from step 2 will be used as the admin password.

[<img src="img/02.png" width="250"/>](img/02.png)

8. The installation is then complete, and you should see the Jenkins dashboard:

[<img src="img/03.png" width="250"/>](img/03.png)

9. Test Jenkins with the "Hello World" pipeline

New Item -> name `hello-world` -> type `Pipeline` -> OK.
In the section *Pipeline* choose "Pipeline script" and paste (see `Jenkinsfile.hello`):

```groovy
pipeline {
    agent any
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
    }
}
```

Save -> *Build Now* -> Console Output of build #1:

```
Started by user admin
[Pipeline] Start of Pipeline
[Pipeline] node
Running on Jenkins in /var/jenkins_home/workspace/hello-world
[Pipeline] {
[Pipeline] stage
[Pipeline] { (Hello)
[Pipeline] echo
Hello World
[Pipeline] }
[Pipeline] // stage
[Pipeline] }
[Pipeline] // node
[Pipeline] End of Pipeline
Finished: SUCCESS
```

`Finished: SUCCESS` confirms that the Jenkins setup works.
