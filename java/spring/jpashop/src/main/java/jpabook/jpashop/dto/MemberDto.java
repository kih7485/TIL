package jpabook.jpashop.dto;

import jakarta.persistence.Embedded;
import jpabook.jpashop.domain.Address;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberDto {
    private String name;

    private Address address;
}
