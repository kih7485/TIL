package resilience.emailservice.mail;

import org.springframework.stereotype.Component;

@Component
public class SMTPClient {
    public void sendMail() {
        // 실제 메일 전송 로직 대신 예외를 발생시켜 실패 상황을 시뮬레이션 합니다.
        throw new SmtpConnectionException("SMTP 연결 실패: 서버에 접속할 수 없습니다.");
    }
}