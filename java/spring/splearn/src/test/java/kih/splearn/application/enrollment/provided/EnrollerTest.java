package kih.splearn.application.enrollment.provided;

import kih.splearn.domain.course.Course;
import kih.splearn.domain.enrollment.Enrollment;
import kih.splearn.domain.enrollment.EnrollmentStatus;
import kih.splearn.domain.member.Member;
import kih.splearn.support.stereotype.ApplicationServiceTest;
import kih.splearn.support.test.BaseApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ApplicationServiceTest
@RequiredArgsConstructor
class EnrollerTest extends BaseApplicationServiceTest {
    final Enroller enroller;

    @Test
    void enroll(){
        Member member = prepareActiveMember();
        Course course = preparePublishedCourse();

        Enrollment enrollment = enroller.enroll(new EnrollRequest(member.getId(), course.getId()));

        Assertions.assertThat(enrollment.getId()).isNotNull();
    }

    @Test
    void enrollFailDuplicate(){
        prepareEnrollment();
        
        assertThatThrownBy(() -> enroller.enroll(new EnrollRequest(enrollment.getMember().getId(), enrollment.getCourse().getId())))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void startStudying(){
        prepareEnrollment();

        Enrollment enrollmentStudying = enroller.startStudying(enrollment.getId());

        Assertions.assertThat(enrollmentStudying.getStatus()).isEqualTo(EnrollmentStatus.STUDYING);
    }

    @Test
    void complete(){
        prepareEnrollment();

        enroller.startStudying(enrollment.getId());
        Enrollment enrollmentCompleted = enroller.complete(enrollment.getId());

        Assertions.assertThat(enrollmentCompleted.getStatus()).isEqualTo(EnrollmentStatus.COMPLETED);
    }

}