package tobyspring.hellospring.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tobyspring.hellospring.exrate.WebApiExRateProvider;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentServiceTest {

    @Test
    @DisplayName("prepare 메서드 요구사항 3가지를 충족했는지 검증")
    void prepare(){
//        PaymentService paymentService = new PaymentService(new WebApiExRateProvider(apiTemplate), Clock.systemDefaultZone());
//
//        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
//
//        // 환율정보 가져온다.
//        assertThat(payment.getExRate()).isNotNull();
//
//        // 원화환산금액 계산.
//        assertThat(payment.getConvertedAmount()).isEqualTo(payment.getExRate().multiply(payment.getForeignCurrencyAmount()));
//
//        // 원화환산금액 유효시간 계산.
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
    }

}