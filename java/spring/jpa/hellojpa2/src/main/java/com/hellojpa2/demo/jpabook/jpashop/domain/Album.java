package com.hellojpa2.demo.jpabook.jpashop.domain;

import jakarta.persistence.Entity;

@Entity
public class Album extends Item{
    private String artist;
}
