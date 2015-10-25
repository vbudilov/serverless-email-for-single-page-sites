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
        EmailService.sendEmail(email);
        return String.valueOf(email);
    }
}
