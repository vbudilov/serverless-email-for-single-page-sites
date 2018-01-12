package com.budilov.lambda.ses

import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import com.amazonaws.services.simpleemail.model.*

data class Email(var fromEmail: String = "", var subject: String = "", var message: String = "")

/**
 * Created by Vladimir Budilov on 10/25/15.
 */
class EmailService(val logger: LambdaLogger?) {

    @Throws(Exception::class)
    fun sendEmail(email: Email) {
        logger?.log("Sending the email: $email")
        val subject = Content().withData("""Subject: ${email.subject} (submitter: ${email.fromEmail})""")
        val textBody = Content().withData(getEmailBody(email.fromEmail, email.message))
        val body = Body().withText(textBody)

        // create the actual message
        val message = Message().withSubject(subject).withBody(body)

        val request = SendEmailRequest()
                .withSource(Properties.destinationEmail)
                .withDestination(Destination()
                        .withToAddresses(Properties.destinationEmail))
                .withMessage(message)
        try {
            logger?.log("Attempting to send the email")
            val client = AmazonSimpleEmailServiceClient()

            // Get the AWS_REGION environment variable
            client.setRegion(Region.getRegion(Regions.fromName(Properties.region)))
            client.sendEmail(request)
            logger?.log("message: Email sent!")
        } catch (ex: Exception) {
            logger?.log("The email was not sent: ${ex.message}")
            throw ex
        }

    }

    fun getEmailBody(email: String, message: String): String {
        return """

        email (submitter): $email

        message: $message
        """
    }
}
