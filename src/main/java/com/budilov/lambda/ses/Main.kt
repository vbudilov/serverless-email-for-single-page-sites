package com.budilov.lambda.ses

/**
 * Created by Vladimir Budilov on 10/24/15.
 */

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler

data class ApiGatewayResponse(val statusCode: Int,
                              val headers: MutableMap<String, String>? = null,
                              val body: String)

class Main : RequestHandler<ApiGatewayRequest.Input, ApiGatewayResponse> {

    override fun handleRequest(request: ApiGatewayRequest.Input?,
                               context: Context?): ApiGatewayResponse? {
        val logger = context?.logger

        val fromEmail = request?.headers?.get("fromEmail")
        val subject = request?.headers?.get("subject")
        val message = request?.headers?.get("message")

        val email:Email?

        if ( fromEmail == null|| subject == null|| message == null)
            return ApiGatewayResponse(statusCode = 200, body = """{ "success": "false", "message": "the payload is null"}""")
        else
            email = Email(fromEmail = fromEmail, subject = subject, message = message)

        val emailService = EmailService(logger)
        logger?.log("email: " + email)

        try {
            emailService.sendEmail(email)
            return ApiGatewayResponse(statusCode = 200, body = """{ "success": "true"}""")
        } catch (exc: Exception) {
            return ApiGatewayResponse(statusCode = 200, body = """{ "success": "false"}""")
        }

    }
}
