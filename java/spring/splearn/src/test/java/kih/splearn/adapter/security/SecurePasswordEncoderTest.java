package kih.splearn.adapter.security;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurePasswordEncoderTest {

    @Test
    void securePasswordEncoder(){
        SecurePasswordEncoder securePasswordEncoder = new SecurePasswordEncoder();

        String passwordHash = securePasswordEncoder.encode("secret2468!@");

        Assertions.assertThat(securePasswordEncoder.matches("secret2468!@", passwordHash)).isTrue();
    }
}