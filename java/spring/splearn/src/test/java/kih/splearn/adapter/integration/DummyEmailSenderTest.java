package kih.splearn.adapter.integration;

import kih.splearn.domain.shared.Email;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out){
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("kih7385@gmail.com"), "subject", "body");
        Assertions.assertThat(out.capturedLines()[0]).isEqualTo("email = Email[address=kih7385@gmail.com]");
    }

}