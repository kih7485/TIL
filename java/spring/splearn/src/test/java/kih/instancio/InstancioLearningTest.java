package kih.instancio;

import org.assertj.core.api.Assertions;
import org.instancio.Instancio;
import org.instancio.Model;
import org.instancio.Select;
import org.junit.jupiter.api.Test;

import static org.instancio.Select.field;

public class InstancioLearningTest {
    @Test
    void user(){
        User user = Instancio.of(User.class)
                .ignore(field(User::getId))
                .generate(field(User::getEmail), gen -> gen.net().email())

                .set(field(User::getStatus), Userstatus.PENDING)
                .create();

        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getEmail()).isNotEmpty();
        Assertions.assertThat(user.getName()).isNotEmpty();
        System.out.println("user = " + user);
    }

    @Test
    void userModel(){
        Model<User> userModel = Instancio.of(User.class)
                .ignore(field(User::getId))
                .generate(field(User::getEmail), gen -> gen.net().email())

                .set(field(User::getStatus), Userstatus.PENDING)
                .toModel();

        for (int i = 0; i < 100; i++) {
            User user = Instancio.of(userModel).create();
            Assertions.assertThat(user.getId()).isNull();
            Assertions.assertThat(user.getEmail()).isNotEmpty();
            Assertions.assertThat(user.getName()).isNotEmpty();
            System.out.println("user = " + user);
        }

    }

    @Test
    void annotation(){
        UserRegistRequest userRegistRequest = Instancio.of(UserRegistRequest.class).create();

        Assertions.assertThat(userRegistRequest.email()).isNotEmpty();
        Assertions.assertThat(userRegistRequest.nickname()).isNotEmpty();
        Assertions.assertThat(userRegistRequest.password()).hasSizeBetween(8, 100);
        System.out.println("userRegistRequest = " + userRegistRequest);
    }
}
