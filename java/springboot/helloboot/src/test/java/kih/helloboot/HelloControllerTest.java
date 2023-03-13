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
        //localhost:8080/hello?name=hi
        TestRestTemplate rest  = new TestRestTemplate();

        ResponseEntity<String> res =
                rest.getForEntity("http://localhost:8080/hello?name={name}", String.class, "Spring");

        //status 200
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

        //header text/plain
        assertThat(res.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE)).startsWith(MediaType.TEXT_PLAIN_VALUE);

        //body
        assertThat(res.getBody()).isEqualTo("hello Spring");
    }
}
