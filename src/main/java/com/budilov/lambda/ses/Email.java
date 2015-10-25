package com.budilov.lambda.ses;

/**
 * Created by Vladimir Budilov on 10/24/15.
 */
public class Email {
    String from;
    String subject;
    String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
