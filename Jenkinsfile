pipeline {
    agent any

    tools {
        jdk 'JDK21'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        DOCKER_IMAGE = 'jeongsangbyuk/springtest:0.0.1'
        K8S_NAMESPACE = 'springtest'
        JAVA_HOME = "${tool 'JDK21'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
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
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker buildx build --platform linux/amd64 -t jeongsangbyuk/springtest:0.0.1 .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                        docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
                             app.push("${DOCKER_IMAGE}")
                             app.push("0.0.1")
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
