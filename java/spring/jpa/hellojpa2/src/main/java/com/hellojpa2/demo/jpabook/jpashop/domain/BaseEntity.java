package com.hellojpa2.demo.jpabook.jpashop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
@MappedSuperclass
public abstract class BaseEntity{
    private String createdBy;
    private LocalDateTime createdDate;
    private String updatedBy;
    private LocalDateTime updatedDate;

}
