package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member); // 중복회원 검증
        repository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = repository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체조회
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
        return repository.findAll();
    }


    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return repository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = repository.findOne(id);
        member.setName(name);
    }
}
