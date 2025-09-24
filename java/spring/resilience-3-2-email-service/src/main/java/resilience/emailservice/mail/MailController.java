package resilience.emailservice.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailSenderService mailSenderService;

    // http://localhost:8081/mail/send 로 요청
    // 예제에서는 POST 요청으로 메일 전송을 시도합니다.
    @GetMapping("/send")
    public String sendMail() {
        mailSenderService.sendEmail();
        return "메일 전송 요청이 접수되었습니다.";
    }
}