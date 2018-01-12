# Sample Lambda Function with SES integration

## Introduction
This simple code is a deployable example of a lambda/ses integration. It's using SAM for deployment purposes, 
so it's a matter of running a couple of commands in order for you to get started quickly and 
efficiently. 
![Architecture](/meta/Lambda-SES-Architecture.png?raw=true)


## Who would want to use this?
If you decided to host a static website on S3 but still want people to send you emails 
through your contact page, this might be a really nice solution for you. 
The combination of S3, API Gateway, Lambda, & SES covers the basis for a 
lot of website owners. 

## Setup
In order for this to work, you need to authorize the receiver (DESTINATION_EMAIL) of your setup. Otherwise SES 
will throw an exception saying that the destination email isn't verified. 
[More info here: Verifying Email Addresses in Amazon SES](https://docs.aws.amazon.com/ses/latest/DeveloperGuide/verify-email-addresses.html)

_Running the cloudformation scripts will provision the needed resources, including 
API Gateway and Lambda, which might incur some costs_

### Get this repo and build it with maven
```
mvn package
```

##### Build your code
```
mvn package
```

##### Package it
```
aws cloudformation package --template-file sam.yaml --s3-bucket code.bucket.name > /tmp/ses-deployment
```

##### Deploy it
```
aws cloudformation deploy --template-file /tmp/ses-deployment --stack-name send-email-stack --parameter-overrides RegionParameter=us-east-1 DestinationEmailParameter=some.email@domain.com --capabilities CAPABILITY_IAM
```

### Test it
```
curl -XPOST -i -H "fromEmail: budilov@gmail.com" -H "subject: Hello World" -H "message: First Test"  https://aaaaaaaa.execute-api.us-east-1.amazonaws.com/Prod/email
```

