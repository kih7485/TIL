package kih.splearn.application.member.provided;

import kih.splearn.SplearnTestConfiguration;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberRegisterRequest;
import kih.splearn.support.stereotype.ApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@ApplicationServiceTest
@RequiredArgsConstructor
class MemberAuthenticatorTest {

    final MemberAuthenticator memberAuthenticator;
    final MemberRegister memberRegister;

    @Test
    void login(){
        var memberRegisterRequest = MemberFixture.createMemberRegisterRequest();
        var member = memberRegister.register(memberRegisterRequest);
        member.activate();

        Member loggedInMember = memberAuthenticator.login(new MemberLoginRequest(memberRegisterRequest.email(), memberRegisterRequest.password()));

        Assertions.assertThat(loggedInMember).isEqualTo(member);
    }

    @Test
    void loginFailedNotActive(){
        var memberRegisterRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(memberRegisterRequest);

        Assertions.assertThatThrownBy(() -> memberAuthenticator.login(new MemberLoginRequest("notexist@email.com", memberRegisterRequest.password())))
                .isInstanceOf(LoginFailedException.class);

    }

    @Test
    void loginFailedWrongPassword(){
        var memberRegisterRequest = MemberFixture.createMemberRegisterRequest();
        memberRegister.register(memberRegisterRequest);

        Assertions.assertThatThrownBy(() -> memberAuthenticator.login(new MemberLoginRequest(memberRegisterRequest.email(), "00000000")))
                .isInstanceOf(LoginFailedException.class);

    }
}