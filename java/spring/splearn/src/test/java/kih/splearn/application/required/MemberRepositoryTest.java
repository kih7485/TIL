package kih.splearn.application.required;

import jakarta.persistence.EntityManager;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.member.Member;
import kih.splearn.domain.member.MemberRegisterInfo;
import kih.splearn.domain.member.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static kih.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static kih.splearn.domain.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor
class MemberRepositoryTest {
    final MemberRepository memberRepository;
    final EntityManager entityManager;

    @Test
    void createMember(){
        Member member = Member.register(createMemberRegisterRequest().toInfo(), createPasswordEncoder());

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
        MemberRegisterInfo memberRegisterRequest = createMemberRegisterRequest().toInfo();
        Member member = Member.register(memberRegisterRequest, createPasswordEncoder());
        memberRepository.save(member);

        Member member2 = Member.register(memberRegisterRequest, createPasswordEncoder());
        assertThatThrownBy(() -> memberRepository.save(member2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}