package study.querydsl.repository;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJPARepositoryTest {
    @Autowired
    EntityManager em;
    @Autowired MemberJPARepository memberJPARepository;

    @Test
    public void basicTest(){
        Member member1 = new Member("member1", 10);
        memberJPARepository.save(member1);

        Member findMember = memberJPARepository.findById(member1.getId()).get();
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member1.getUsername());
    }

    @Test
    public void basicTest_queryDsl(){
        Member member1 = new Member("member1", 10);
        memberJPARepository.save(member1);

        Member findMember = memberJPARepository.findById(member1.getId()).get();
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member1.getUsername());

        List<Member> result1 = memberJPARepository.findAll_queryDsl();
        Assertions.assertThat(result1).containsExactly(member1);

        List<Member> result2 = memberJPARepository.findByUserName_queryDsl("member1");
        Assertions.assertThat(result2).containsExactly(member1);
    }

    @Test
    public void searchTest(){
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);

        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);


        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJPARepository.search(condition);
        Assertions.assertThat(result).extracting("username").containsExactly("member4");
//        em.flush();
//        em.clear();
    }
}