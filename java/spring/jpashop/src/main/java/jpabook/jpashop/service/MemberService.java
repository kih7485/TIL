package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    //회원가입
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증
        repository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

    }

    //회원 전체조회


}
