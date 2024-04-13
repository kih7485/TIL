package com.hellojpa2.demo.jpabook.jpashop;

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
            Movie movie = new Movie();
            movie.setDirector("Aaa");
            movie.setActor("bbb");
            movie.setName("바람과 함께 사라지다.");
            movie.setPrice(10_000);
            em.persist(movie);
            tx.commit();
        }catch (Exception e){
            tx.rollback();
        }finally {
            em.close();
        }

        emf.close();
    }
}
