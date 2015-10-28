package com.budilov.lambda.ses;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;

/**
 * Created by Vladimir Budilov on 10/25/15.
 */
public class EmailService {

    private static String TO = PropUtil.get("toEmail"); // Replace with a "To" address. If your account is still in the

    private static BasicAWSCredentials awsCreds = new BasicAWSCredentials(PropUtil.get("accessKey"), PropUtil.get("secretKey"));

    public static void sendEmail(Email email) throws Exception {

        Destination destination = new Destination().withToAddresses(new String[]{TO});

        Content subject = new Content().withData(email.getSubject() + " (from " + email.getFrom() + ")");
        Content textBody = new Content().withData(email.getMessage());
        Body body = new Body().withText(textBody);

        Message message = new Message().withSubject(subject).withBody(body);

        SendEmailRequest request = new SendEmailRequest().withSource(PropUtil.get("fromEmail"))
                .withDestination(destination)
                .withMessage(message);

        try {
            System.out.println("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
            AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(awsCreds);
            Region REGION = Region.getRegion(Regions.US_EAST_1);
            client.setRegion(REGION);

            client.sendEmail(request);
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
            throw ex;
        }
    }
}
