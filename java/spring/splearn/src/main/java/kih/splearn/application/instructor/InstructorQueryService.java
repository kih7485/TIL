package kih.splearn.application.instructor;

import kih.splearn.application.instructor.provided.InstructorFinder;
import kih.splearn.application.instructor.required.InstructorRepository;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.member.Member;
import kih.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class InstructorQueryService implements InstructorFinder {
    private final InstructorRepository instructorRepository;

    @Override
    public Instructor find(Long instructorId) {
        return instructorRepository.findById(instructorId).orElseThrow(
                () -> new IllegalArgumentException("강사를 찾을 수 없습니다. ID: " + instructorId)
        );
    }

    @Override
    public Optional<Instructor> findByMember(Long memberId) {
        return instructorRepository.findByMemberId(memberId);
    }
}
