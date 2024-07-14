package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception {
        Member member = new Member();
        member.setName("kang");

        Long saveId = memberService.join(member);

        assertThat(member).isEqualTo(memberRepository.findOne(saveId));

    }

    @Test
    void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("kang");
        Member member2 = new Member();
        member2.setName("kang");

        memberService.join(member1);


        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);
        });

    }
}