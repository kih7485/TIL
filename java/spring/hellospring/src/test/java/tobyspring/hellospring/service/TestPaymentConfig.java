package tobyspring.hellospring.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration
public class TestPaymentConfig {
//    @Bean
//    public PaymentService paymentService() {
//        return new PaymentService(cachedExRateProvider(), clock());
//    }

//    @Bean
//    public ExRateProvider cachedExRateProvider() {
//        return new CachedExRateProvider(exRateProvider());
//    }
//    @Bean
//    public ExRateProvider exRateProvider(){
//        return new WebApiExRateProvider(apiTemplate);
//    }

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }

}