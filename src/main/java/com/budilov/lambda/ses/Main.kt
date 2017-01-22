package com.budilov.lambda.ses

/**
 * Created by Vladimir Budilov on 10/24/15.
 */

import com.amazonaws.services.lambda.runtime.Context

class Main {

    fun sendMail(email: Email, context: Context): String {
        val logger = context.logger
        val emailService = EmailService(logger)
        logger.log("email: " + email)

        var success = "true"

        try {
            emailService.sendEmail(email)
        } catch (exc: Exception) {
            success = "false"
        }

        return getResponse(success)
    }


    fun getResponse(success:String):String {
        return """{ "success": "REPLACE"}""".replace("REPLACE", success)
    }
}
