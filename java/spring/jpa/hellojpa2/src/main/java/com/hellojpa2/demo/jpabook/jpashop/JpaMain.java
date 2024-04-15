package com.hellojpa2.demo.jpabook.jpashop;

import com.hellojpa2.demo.jpabook.jpashop.domain.Book;
import com.hellojpa2.demo.jpabook.jpashop.domain.Member;
import com.hellojpa2.demo.jpabook.jpashop.domain.Movie;
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
            Member member = new Member();
            member.setUsername("hello");
            em.persist(member);

            em.flush();
            em.clear();

            Member findMember = em.getReference(Member.class, member.getId());
//            Member findMember = em.find(Member.class, member.getId());
//            log.info("memebername={}", findMember.getUsername());

            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
