pipeline {
    agent any

    tools {
        jdk 'JDK21'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        K8S_PK = credentials('ncp-key')
        DOCKER_IMAGE = 'jeongsangbyuk/springtest:0.0.1'
        K8S_NAMESPACE = 'springtest'
        JAVA_HOME = "${tool 'JDK21'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
        env.TARGET_HOST = "-p 33034 root@106.10.52.26"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh './gradlew clean build'
                    sh "cp /var/lib/jenkins/workspace/bbogak-t1/build/libs/rate-limit-0.0.1-SNAPSHOT.jar /var/lib/jenkins/workspace/bbogak-t1/"
                }
            }
        }

        stage('Login'){
            steps{
                sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin' // docker hub 로그인
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    sh 'docker buildx build --push --platform linux/amd64 -t $DOCKER_IMAGE .'
                }
            }
        }
        stage('ssh-test') {
            step{
                script{
                    sshagent (credentials: $K8S_PK) {
                        sh 'ssh -o StrictHostKeyChecking=no "uptime"'
                        sh 'kubectl rollout status deployment/deploy-bbogak-api-dev -n bbogak-api'
                    }
                }
            }
        }
        
    }

    post {
        success {
            echo 'Deployment was successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}
