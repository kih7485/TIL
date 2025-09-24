package resilience.emailservice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {

    @Autowired
    private SMTPClient smtpClient;

    public void sendEmail() {
        // SMTPClient에서 예외가 발생하면 처리하지 않고 전파됩니다.
        smtpClient.sendMail();
    }
}