package tobyspring.hellospring.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tobyspring.hellospring.api.ApiTemplate;
import tobyspring.hellospring.api.ErApiExRateExtractor;
import tobyspring.hellospring.api.SimpleApiExecutor;
import tobyspring.hellospring.exrate.RestTemplateExRateProvider;
import tobyspring.hellospring.payment.ExRateProvider;
import tobyspring.hellospring.exrate.WebApiExRateProvider;
import tobyspring.hellospring.payment.PaymentService;

import java.time.Clock;

@Configuration
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

//    @Bean
//    public ApiTemplate apiTemplate(){
//        return new ApiTemplate(new SimpleApiExecutor(), new ErApiExRateExtractor());
//    }
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }
//    @Bean
//    public ExRateProvider exRateProvider(){
//        return new WebApiExRateProvider(apiTemplate());
//    }

    @Bean
    public ExRateProvider exRateProvider(){
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }

}
