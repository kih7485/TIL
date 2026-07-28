package kih.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void profile(){
        new Profile("kih7485");
        new Profile("korea7485");
        new Profile("qwe4127");
        new Profile("");

    }

    @Test
    void profileFail(){
        assertThatThrownBy(() -> new Profile("toolonglong12313123123")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new Profile("한국어")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url(){
         var profile = new Profile("kih7485");

         Assertions.assertThat(profile.url()).isEqualTo("@kih7485");
    }
}