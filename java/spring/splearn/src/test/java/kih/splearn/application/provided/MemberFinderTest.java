package kih.splearn.application.provided;

import jakarta.persistence.EntityManager;
import kih.splearn.SplearnTestConfiguration;
import kih.splearn.application.member.provided.MemberFinder;
import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.MemberFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberFinderTest(MemberFinder memberFinder, MemberRegister memberRegister, EntityManager entityManager) {

    @Test
    void find(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member member1 = memberFinder.find(member.getId());

        Assertions.assertThat(member.getId()).isEqualTo(member1.getId());
    }
}