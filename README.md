## Service

### https://www.hyosungcmsplus.site

## Preview

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/view.png">

## System Architecture

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/system-architecture1.png">

## Infra Architecture

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/infra-architecture.png">

## Kafka Architecture

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/kafka-architecture.png">

## Branch Strategy

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/branch-strategy.png">

## Monitoring

### Elasticsearch Logstash Kibana

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/kibana.png">

### Prometheus Grafana

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/grafana.png">

### Kafka

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/kafka-monitoring.png">

## Graph for Kafka Batch's Tuning

<img src = "https://github.com/rlatkd/cms-plus/blob/dev/assets/kafka-batch-tuning-graph.png">

## Repository Structure

```
📁 cms
 ├──── 📁 .github
 │      ├──── 📁 ISSUE_TEMPLATE
 │      │      ├──── 📄 BUILD.md
 │      │      ├──── 📄 CICD.md
 │      │      ├──── 📄 DOCS.md
 │      │      ├──── 📄 FEAT.md
 │      │      ├──── 📄 FIX.md
 │      │      ├──── 📄 PERF.md
 │      │      ├──── 📄 REFACTOR.md
 │      │      ├──── 📄 STYLE.md
 │      │      └──── 📄 TEST.md
 │      └──── 📁 workflows
 │             ├──── 📄 ANALYSIS_CICD.yml
 │             ├──── 📄 BATCH_CICD.yml
 │             ├──── 📄 DEV_TRIGGER.yml
 │             ├──── 📄 FE_CD.yml
 │             ├──── 📄 FE_CI.yml
 │             ├──── 📄 FEAT_TRIGGER.yml
 │             ├──── 📄 IMAGE_RESIZE.yml
 │             ├──── 📄 MAIN_CICD.yml
 │             ├──── 📄 MESSAGING_CICD.yml
 │             ├──── 📄 PAYMENT_CICD.yml
 │             └──── 📄 PR_ALARM.yml
 ├──── 📁 client
 │      ├──── 📁 public
 │      │      └──── 📄 default_excel.xlsx
 │      │──── 📁 src
 │      │      ├──── 📁 apis
 │      │      ├──── 📁 assets
 │      │      ├──── 📁 components
 │      │      ├──── 📁 hooks
 │      │      ├──── 📁 labs
 │      │      ├──── 📁 pages
 │      │      ├──── 📁 routes
 │      │      ├──── 📁 stores
 │      │      ├──── 📁 styles
 │      │      ├──── 📁 utils
 │      │      ├──── 📄 App.jsx
 │      │      └──── 📄 main.jsx
 │      ├──── 📄 .env
 │      ├──── 📄 .eslintrc.cjs
 │      ├──── 📄 index.html
 │      ├──── 📄 jsconfig.json
 │      ├──── 📄 postcss.config.js
 │      ├──── 📄 tailwind.config.js
 │      └──── 📄 vite.config.js
 ├──── 📁 convention
 │      ├──── 📄 CATALOG
 │      └──── 📄 COMMIT_LINT
 ├──── 📁 database
 │      ├──── 📄 ddl.txt
 │      ├──── 📄 Dockerfile
 │      └──── 📄 ini.sql
 ├──── 📁 DEPLOY_FILE
 │      ├──── 📁 client
 │      └──── 📁 server
 ├──── 📁 DEPLOY_LOG
 │      ├──── 📁 client
 │      └──── 📁 server
 ├──── 📁 infra
 │      ├──── 📄 lambda.py
 │      ├──── 📄 request.zip
 │      ├──── 📄 task-definition-analysis.json
 │      ├──── 📄 task-definition-batch.json
 │      ├──── 📄 task-definition-main.json
 │      ├──── 📄 task-definition-messaging.json
 │      └──── 📄 task-definition-payment.json
 ├──── 📁 server
 │      ├──── 📁 config
 │      ├──── 📁 log
 │      ├──── 📁 src
 │      │      ├──── 📁 docs
 │      │      │      └──── 📁 asciidoc
 │      │      ├──── 📁 main
 │      │      │      │──── 📁 generated
 │      │      │      │──── 📁 java
 │      │      │      │      │──── 📁 config
 │      │      │      │      │──── 📁 domain
 │      │      │      │      └──── 📁 util
 │      │      │      └──── 📁 resources
 │      │      └──── 📁 test
 │      └──── 📄 Dockerfile
 ├──── 📁 server-analysis
 │      └──── 📁 src
 │      │      ├──── 📁 data
 │      │      ├──── 📁 notebooks
 │      │      ├──── 📄 server.py
 │      │      └──── 📄 trigger.py
 │      ├──── 📄 Dockerfile.web
 │      ├──── 📄 jupyter-api.code-workspace
 │      ├──── 📄 Pipfile
 │      ├──── 📄 Procfile
 │      ├──── 📄 run.sh
 │      └──── 📄 requirements.txt
 ├──── 📁 server-batch
 ├──── 📁 server-kafka
 │      └──── 📄 docker-compose.yml
 ├──── 📁 server-messaging
 ├──── 📁 server-monitoring
 │      ├──── 📁 elasticsearch
 │      ├──── 📁 kibana
 │      ├──── 📁 logstash
 │      ├──── 📁 prometheus
 │      └──── 📄 docker-compose.yml
 ├──── 📁 server-payment
 └──── 📄 docker-compose.yml
```

- .github: 이슈 템플릿, 워크플로우 파일들
- client: 리액트 앱
- convention: 협업 세팅 문서들
- database: 데이터베이스 파일들
- DEPLOY_FILE: 배포 시 배포용 파일들
- DEPLOY_LOG: 배포 시 배포내역 로그들
- infra: 기타 AWS 인프라 설정에 필요한 파일들
- server: 메인 서비스
- server-analysis: 통계 서버
- server-batch: 배치 서버
- server-kafka: 카프카 서버
- server-messaging: 메시징 서버
- server-payment: 결제 서버
- docker-compose.yml: 로컬에서 사용하는 공동 세팅