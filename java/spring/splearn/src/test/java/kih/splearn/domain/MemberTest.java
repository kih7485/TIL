package kih.splearn.domain;

import kih.splearn.domain.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static kih.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static kih.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;
    MemberRegisterInfo registerRequest;

    @BeforeEach
    void setup(){
        this.passwordEncoder = createPasswordEncoder();
        registerRequest = createMemberRegisterRequest().toInfo();
        member = Member.register(registerRequest, passwordEncoder);
    }

    @Test
    void registerMember(){
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void activeFail(){

        member.activate();

        assertThatThrownBy(() ->{
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactivate(){
        member.activate();
        member.deactivate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void verifyPassword(){
        assertThat(member.verifyPassword(registerRequest.password(), passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changePassword(){
        member.changePassword("very", passwordEncoder);

        Assertions.assertThat(member.verifyPassword("very", passwordEncoder)).isTrue();
    }

    @Test
    void shoudBeActive(){
        assertThat(member.isActive()).isFalse();

        member.activate();
        assertThat(member.isActive()).isTrue();
    }

    @Test
    void invalidEmail(){
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email").toInfo(), passwordEncoder)
        ).isInstanceOf(IllegalStateException.class);
        member = Member.register(createMemberRegisterRequest().toInfo(), passwordEncoder);
    }

    @Test
    void updateInfo(){
        member.activate();
        MemberInfoUpdateRequest updateRequest = new MemberInfoUpdateRequest("leo", "kih7485", "자기소개");
        member.updateInfo(updateRequest);

        Assertions.assertThat(member.getNickname()).isEqualTo(updateRequest.nickname());
        Assertions.assertThat(member.getDetail().getProfile().address()).isEqualTo(updateRequest.profileAddress());
        Assertions.assertThat(member.getDetail().getIntroduction()).isEqualTo(updateRequest.introduction());
    }

    @Test
    void updateInfoFail(){
        assertThatThrownBy(() -> {
            var request  = new MemberInfoUpdateRequest("Leo", "kih7485", "자기소개");
            member.updateInfo(request);
        }).isInstanceOf(IllegalStateException.class);
    }
}