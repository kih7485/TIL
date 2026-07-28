package kih.splearn.application.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import kih.splearn.SplearnTestConfiguration;
import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.*;
import kih.splearn.domain.member.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(MemberRegister memberRegister, EntityManager entityManager) {
    @Test
    void register(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        System.out.println("member = " + member);
        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

    }
    @Test
    void duplicateEmailFail(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                        .isInstanceOf(DuplicateEmailException.class);
        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void memberRegisterRequestFail(){
        extracted(new MemberRegisterRequest("kih@splearn.app", "kih", "longsecret"));
        extracted(new MemberRegisterRequest("kih@splearn.app", "kihsssasdqw112131aaas", "longsecret"));
        extracted(new MemberRegisterRequest("kihsplearn.app", "kihsssasdqw112131aaas", "longsecret"));
    }

    private void extracted(MemberRegisterRequest member) {
        assertThatThrownBy(() -> memberRegister.register(member))
            .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @Transactional
    void activate(){
        Member member = registerMemger();

        member = memberRegister.activate(member.getId());

        entityManager.flush();

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    @Transactional
    void deactivate(){
        Member member = registerMemger();

        member = memberRegister.activate(member.getId());

        entityManager.flush();
        entityManager.clear();

        member = memberRegister.deactivate(member.getId());

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        Assertions.assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    private Member registerMemger() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();
        return member;
    }

    @Test
    void updateInfo(){
        Member member = registerMemger();
        member = memberRegister.activate(member.getId());
        entityManager.flush();
        entityManager.clear();
        MemberInfoUpdateRequest updateRequest = new MemberInfoUpdateRequest("leolsa", "kih7485", "자기소개");

        member = memberRegister.updateInfo(member.getId(), updateRequest);

        Assertions.assertThat(member.getDetail().getProfile().address()).isEqualTo("kih7485");
    }
}
