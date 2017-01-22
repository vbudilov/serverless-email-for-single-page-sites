# Sample Lambda Function with SES integration

## Introduction
This simple code is a deployable example of a lambda/ses integration. 

##Who would want to use this?
If you decided to host a static website on S3 but still want people to send you emails through your contact page, this might be a really nice solution for you. The combination of S3, Lambda, & SES covers the basis for a lot of website owners. 

## Setup
You'll need to setup an api gateway, a lambda IAM role, a lambda function, and ses

###Get this repo and build it with maven
```
mvn package
```

The output jar file is stored under ```target/lambda-ses-1.2-SNAPSHOT.jar```

###Create an API Gateway based on the below swagger definition
```
swagger: "2.0"
info:
  version: "2016-12-16T02:36:33Z"
  title: "titleoftheapigateway"
host: "url.execute-api.us-east-1.amazonaws.com"
basePath: "/prod"
schemes:
- "https"
paths:
  /email:
    post:
      produces:
      - "application/json"
      responses:
        200:
          description: "200 response"
          schema:
            $ref: "#/definitions/Empty"
          headers:
            Access-Control-Allow-Origin:
              type: "string"
    options:
      consumes:
      - "application/json"
      produces:
      - "application/json"
      responses:
        200:
          description: "200 response"
          schema:
            $ref: "#/definitions/Empty"
          headers:
            Access-Control-Allow-Origin:
              type: "string"
            Access-Control-Allow-Methods:
              type: "string"
            Access-Control-Allow-Headers:
              type: "string"
definitions:
  Empty:
    type: "object"
    title: "Empty Schema"
```

###Create a lambda role
```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ses:*"
      ],
      "Resource": "*"
    }
  ]
}
```

###Create a lambda function
You can run the below command to create the function, just make sure you're ok with the parameters. Pricing depends on the memory-size, so you might want to choose a smaller footprint there

```
LAMBDA_FUNCTION=lambda-ses-email-with-env-variables
ACCOUNT=011115911111
JAR_LOCATION=target/lambda-ses-1.2-SNAPSHOT.jar
PROFILE=budilov
ENV_VAR_EMAIL=info@domain.com
REGION=us-east-1

aws lambda create-function \
--region ${REGION} \
--function-name ${LAMBDA_FUNCTION} \
--zip-file fileb://${JAR_LOCATION} \
--role arn:aws:iam::${ACCOUNT}:role/lambda-to-ses  \
--environment Variables={TO_EMAIL=${ENV_VAR_EMAIL}} \
--handler com.budilov.lambda.ses.Main::sendMail \
--runtime java8 \
--timeout 45 \
--memory-size 256 \
--profile ${PROFILE}
```

Run the following command if you need to update the function with the new code (after you already created it)

```
aws lambda update-function-code --region ${REGION} \
--function-name ${LAMBDA_FUNCTION} \
--zip-file fileb://${JAR_LOCATION} \
--profile ${PROFILE}

```
###Setup SES
In order for this to work, you'll need to authorize the receivers and the senders. 
Meaning, if all of your receivers and senders are coming from the same domain, you can just 
authorize/verify the domain name. If not, then you can just authorize/verify the sender and the 
receiver

###Test it
```
curl -H "Content-Type: application/json" -X POST \
-d '{"fromEmail" : "budilov@gmail.com", "subject" : "Hello World", "message" : "First Test"}' \
https://someassigneddomainname.us-east-1.amazonaws.com/prod/email
```

