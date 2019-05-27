# Overture Provision
This repo contains the helm values and the Jenkins script to deploy the overture stack

## Status
under constructions, missing other overture charts.

## Usage
We use Jenkins parameterized pipeline job to execute helm/Jenkinsfile.groovy
The script needs the following env vars:

```groovy
def component=env.OP_FOLDER_NAME
def deployTo=env.OP_OVERTURE_ENV
def chartName=env.OP_CHART_NAME
def helmRepoUrl=env.OP_HELM_REPO_URL
def helmRepoName=env.OP_HELM_REPO_NAME
def argsLine=env.OP_ARGS_LINE
```

## Charts
- Elasticsearch: https://github.com/elastic/helm-charts/tree/master/elasticsearch
- confluent platform (kafka, zookeeper, kafka proxy, etc): https://github.com/confluentinc/cp-helm-charts
- Meastro: source: https://github.com/overture-stack/helm-charts, repo: https://github.com/overture-stack/charts-server
