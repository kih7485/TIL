package kih.splearn.application.instructor.provided;

import jakarta.validation.ConstraintViolationException;
import kih.splearn.application.instructor.required.InstructorRepository;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.instructor.InstructorFixture;
import kih.splearn.domain.instructor.InstructorStatus;
import kih.splearn.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class InstructorApplicationTest {
    final InstructorApplication instructorApplication;
    final InstructorRepository instructorRepository;
    final MemberRepository memberRepository;

    @Test
    void apply(){
        Instructor instructor = preparePendingInstructor();

        Assertions.assertThat(instructor.getId()).isNotNull();
        Assertions.assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);

        instructorRepository.findById(instructor.getId()).orElseThrow();
    }

    @Test
    void approve(){
        Instructor instructor = instructorApplication.approve(preparePendingInstructor().getId());

        Assertions.assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    void reject(){
        Instructor instructor = instructorApplication.reject(preparePendingInstructor().getId());

        Assertions.assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.REJECTED);
    }

    @Test
    void duplicateApply(){
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        instructorApplication.apply(InstructorFixture.createApplyRequest(member));

        Assertions.assertThatThrownBy(() -> instructorApplication.apply(InstructorFixture.createApplyRequest(member)))
                .isInstanceOf(DuplicateInstructorApplicationException.class);
    }

    private Instructor preparePendingInstructor() {
        Member member = MemberFixture.createActiveMember();
        memberRepository.save(member);

        return instructorApplication.apply(InstructorFixture.createApplyRequest(member));
    }
}