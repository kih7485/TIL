package kih.splearn.domain;

import kih.splearn.domain.shared.Email;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equality(){
        var email1 = new Email("kih@gmail.com");
        var email2 = new Email("kih@gmail.com");

        Assertions.assertThat(email1).isEqualTo(email2);
    }

}