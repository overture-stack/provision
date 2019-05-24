pipeline {
    agent {
        kubernetes {
            label 'provision-executor'
            yaml """
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: helm
    image: alpine/helm:2.12.3
    command:
    - cat
    tty: true
"""
        }
    }
    stages {
        stage('Deploy') {
            steps {
                container('helm') {
                    withCredentials([file(credentialsId:'4ed1e45c-b552-466b-8f86-729402993e3b', variable: 'KUBECONFIG')]) {
                        sh 'helm init --client-only'
                        sh "helm ls --kubeconfig $KUBECONFIG"
                        sh 'helm repo add confluentinc https://confluentinc.github.io/cp-helm-charts/'
                        sh """
                            helm upgrade --kubeconfig $KUBECONFIG --install --namespace=overture-qa confluent-qa \\
                            cp-helm-charts -f helm/kafka/qa/values.yaml
                           """
                    }
                }
            }
        }
    }
}