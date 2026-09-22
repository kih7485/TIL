package kih.splearn.application.enrollment.required;

import kih.splearn.domain.course.Course;
import kih.splearn.domain.enrollment.Enrollment;
import kih.splearn.domain.member.Member;
import kih.splearn.support.test.BaseRepositoryTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

@DataJpaTest
@RequiredArgsConstructor
class EnrollmentRepositoryTest extends BaseRepositoryTest {
    final EnrollmentRepository enrollmentRepository;

    @Test
    void saveAndFindId(){
        Member member = prepareActiveMember();
        Course course = preparePublishedCourse();

        Enrollment enroll = Enrollment.enroll(member, course);
        enroll = enrollmentRepository.save(enroll);

        Assertions.assertThat(enroll.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        Enrollment found = enrollmentRepository.findById(enroll.getId()).orElseThrow();

        Assertions.assertThat(found).isEqualTo(enroll);
    }

    @Test
    void findByMemberId(){
        Member member1 = prepareActiveMember();
        Member member2 = prepareActiveMember();

        Enrollment enrollment1_1 = prepareEnrollment(member1, preparePublishedCourse());
        Enrollment enrollment1_2 = prepareEnrollment(member1, preparePublishedCourse());
        Enrollment enrollment2 = prepareEnrollment(member2, preparePublishedCourse());

        List<Enrollment> enrollments1 = enrollmentRepository.findByMemberId(member1.getId());
        Assertions.assertThat(enrollments1).hasSize(2).containsExactly(enrollment1_1, enrollment1_2);

        List<Enrollment> enrollments2 = enrollmentRepository.findByMemberId(member2.getId());
        Assertions.assertThat(enrollments2).hasSize(1).containsExactly(enrollment2);
    }

    @Test
    void findByMemberIdAndCourseId(){
        Member member1 = prepareActiveMember();
        Member member2 = prepareActiveMember();

        Course course1 = preparePublishedCourse();
        Course course2= preparePublishedCourse();

        Enrollment enrollment1 = prepareEnrollment(member1, course1);
        Enrollment enrollment2 = prepareEnrollment(member2, course2);

        Enrollment enrollmentEq1 = enrollmentRepository.findByMemberIdAndCourseId(member1.getId(), course1.getId()).orElseThrow();
        Enrollment enrollmentEq2 = enrollmentRepository.findByMemberIdAndCourseId(member2.getId(), course2.getId()).orElseThrow();
        boolean enrollmentNotEq2 = enrollmentRepository.findByMemberIdAndCourseId(member1.getId(), course2.getId()).isPresent();

        Assertions.assertThat(enrollmentEq1).isEqualTo(enrollment1);
        Assertions.assertThat(enrollmentEq2).isEqualTo(enrollment2);
        Assertions.assertThat(enrollmentNotEq2).isFalse();
    }

}