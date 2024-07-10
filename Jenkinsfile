pipeline {
    agent any
    
    tools {
        jdk 'openjdk-21-jdk'
    }

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials-id')
        KUBECONFIG_CREDENTIALS = credentials('kubeconfig-credentials-id')
        DOCKER_IMAGE = 'jeongsangbyuk/springtest:0.0.1'
        K8S_NAMESPACE = 'springtest'
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
                    docker.withRegistry('https://index.docker.io/v1/', 'DOCKERHUB_CREDENTIALS') {
                        sh 'docker push ${DOCKER_IMAGE}:0.0.1'
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    withCredentials([file(credentialsId: 'KUBECONFIG_CREDENTIALS', variable: 'KUBECONFIG')]) {
                        sh '''
                        kubectl --kubeconfig=$KUBECONFIG set image deployment/your-app your-app=${DOCKER_IMAGE}:0.0.1 -n ${K8S_NAMESPACE}
                        kubectl --kubeconfig=$KUBECONFIG rollout status deployment/deploy-springtest -n ${K8S_NAMESPACE}
                        '''
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
