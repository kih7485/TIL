package kih.splearn.application.instructor.provided;

import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.member.Member;

import java.util.Optional;

/**
 * 강사 조회
 * */
public interface InstructorFinder {
    Instructor find(Long instructorId);

    Optional<Instructor> findByMember(Long memberId);

    default Optional<Instructor> findByMember(Member member){
        return findByMember(member.getId());
    }
}
