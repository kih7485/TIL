package resilience.emailservice.mail;

import resilience.emailservice.exception.CustomUncheckedException;

public class SmtpConnectionException extends CustomUncheckedException {

    public SmtpConnectionException(String message) {
        super(message);
    }

}