def component=env.OP_FOLDER_NAME
def deployTo=env.OP_OVERTURE_ENV
def chartName=env.OP_CHART_NAME
def helmRepoUrl=env.OP_HELM_REPO_URL
def helmRepoName=env.OP_HELM_REPO_NAME
def argsLine=env.OP_ARGS_LINE

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
                        sh 'env'
                        sh 'helm init --client-only'
                        sh "helm ls --kubeconfig $KUBECONFIG"
                        sh "helm repo add $helmRepoName $helmRepoUrl"
                        sh """
                            helm upgrade --kubeconfig $KUBECONFIG --install --namespace=overture-$deployTo $component-overture-$deployTo \\
                            $helmRepoName/$chartName -f helm/$component/$deployTo/values.yaml $argsLine
                           """
                    }
                }
            }
        }
    }
}