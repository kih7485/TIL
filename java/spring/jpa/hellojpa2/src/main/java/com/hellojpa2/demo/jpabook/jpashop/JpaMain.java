package com.hellojpa2.demo.jpabook.jpashop;

import com.hellojpa2.demo.jpabook.jpashop.domain.Member;
import com.hellojpa2.demo.jpabook.jpashop.domain.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JpaMain {

    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        try{
            Team teamA = new Team();
            teamA.setName("TeamA");
            em.persist(teamA);

            Member member = new Member();
            member.setUsername("Member1");
            member.changeTeam(teamA);
            em.persist(member);

//            em.flush();
//            em.clear();

            Member findMember = em.find(Member.class, member.getId());
            Team team1 = findMember.getTeam();
            List<Member> members = team1.getMembers();
            for (Member m : members) {
                log.info("m={}", m.getUsername());
            }

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
