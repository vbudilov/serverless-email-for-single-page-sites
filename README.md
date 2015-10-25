# Sample Lambda Function

## Introduction

## Setup


### Prep AWS CLI Commands

####Create role
arn:aws:iam::540403165297:role/awsLambdaExecution

####Create lambda
```
aws lambda create-function \
--region us-east-1 \
--function-name lambda-ses-email \
--zip-file fileb:///home/vova/Dev/common-java/lambda-ses/target/lambda-ses-1.0-SNAPSHOT.jar \
--role arn:aws:iam::540403165297:role/awsLambdaExecution  \
--handler com.budilov.lambda.ses.Main::sendMail \
--runtime java8 \
--timeout 15 \
--memory-size 512 \
--profile work
```