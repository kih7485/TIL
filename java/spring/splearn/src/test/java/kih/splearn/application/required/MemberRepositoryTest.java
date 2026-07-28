package kih.splearn.application.required;

import jakarta.persistence.EntityManager;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static kih.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static kih.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void createMember(){
        Member member = Member.create(createMemberRegisterRequest(), createPasswordEncoder());

        assertThat(member.getId()).isNull();

        memberRepository.save(member);

        assertThat(member.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        Member found = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(found.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(found.getDetail().getRegisteredAt() ).isNotNull();
    }

    @Test
    void duplicateEmailFail(){
        Member member = Member.create(createMemberRegisterRequest(), createPasswordEncoder());
        memberRepository.save(member);

        Member member2 = Member.create(createMemberRegisterRequest(), createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}