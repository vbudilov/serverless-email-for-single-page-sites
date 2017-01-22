package com.budilov.lambda.ses

import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.runtime.LambdaLogger
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import com.amazonaws.services.simpleemail.model.*


/**
 * Created by Vladimir Budilov on 10/25/15.
 */
class EmailService(val logger: LambdaLogger) {

    private val _TO_EMAIL = "TO_EMAIL"

    @Throws(Exception::class)
    fun sendEmail(email: Email) {

        // get the TO_EMAIL environment variable
        val destination = Destination().withToAddresses(*arrayOf(System.getenv(_TO_EMAIL)))

        val subject = Content().withData(getSubject(email.from, email.subject))
        val textBody = Content().withData(getEmailBody(email.from, email.message))
        val body = Body().withText(textBody)

        // create the actual message
        val message = Message().withSubject(subject).withBody(body)

        val request = SendEmailRequest().withSource(System.getenv("TO_EMAIL"))
                .withDestination(destination)
                .withMessage(message)
        try {
            logger.log("message: Attempting to send the email")
            val client = AmazonSimpleEmailServiceClient()

            // Get the AWS_REGION environment variable
            client.setRegion(Region.getRegion(Regions.fromName(System.getenv("AWS_REGION"))))
            client.sendEmail(request)
            logger.log("message: Email sent!")
        } catch (ex: Exception) {
            logger.log("message: The email was not sent ")
            logger.log("error: " + ex.message)
            throw ex
        }

    }

    fun getSubject(email:String, subject:String):String {
        return """$subject (submitter: $email)"""
    }

    fun getEmailBody(email:String, message:String):String {
        return """
        email (submitter): $email

        message: $message
        """
    }
}
