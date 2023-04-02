package kih.helloboot;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.*;

public class HelloControllerTest {

    @Test
    void HelloApi(){
        HelloController helloController = new HelloController(name -> name);
        String inhan = helloController.hello("inhan");

        Assertions.assertThat(inhan).isEqualTo("inhan");
    }

    @Test
    void failHelloController(){
        HelloController helloController = new HelloController(name -> name);


        assertThatThrownBy(() -> {
            String inhan = helloController.hello(null);
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            String inhan = helloController.hello("");
        }).isInstanceOf(IllegalArgumentException.class);
//        Assertions.assertThat(inhan).isEqualTo(null);
    }
}
