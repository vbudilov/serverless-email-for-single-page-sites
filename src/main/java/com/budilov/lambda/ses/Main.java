package com.budilov.lambda.ses;

/**
 * Created by Vladimir Budilov on 10/24/15.
 */

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

public class Main {

    public String sendMail(Email email, Context context) {
        LambdaLogger logger = context.getLogger();
        logger.log("received : " + email);
        String response = "{ \"success\": \"true\"}";
        try {
            EmailService.sendEmail(email);
        } catch (Exception exc) {
            response = "{ \"success\": \"false\"}";
        }

        return String.valueOf(response);
    }
}
