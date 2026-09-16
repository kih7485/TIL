package kih.splearn.domain.enrollment;

import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    @Test
    void enroll(){
        Member member = MemberFixture.createActiveMember();
        Course course = CourseFixture.createPublishedCourse();

        Enrollment enroll = Enrollment.enroll(member, course);

        Assertions.assertThat(enroll.getStatus()).isEqualTo(EnrollmentStatus.ENROLLED);
        Assertions.assertThat(enroll.getEnrolledAt()).isNotNull();
    }

    @Test
    void enrollFailNotPublishedCourse(){
        Member member = MemberFixture.createActiveMember();
        Course course = CourseFixture.createCourse();


        assertThatThrownBy(() -> Enrollment.enroll(member, course))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void startStudying(){
        Enrollment enrollment = EnrollmentFixture.createEnrollment();

        enrollment.startStudying();

        Assertions.assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.STUDYING);
        
        assertThatThrownBy(() -> enrollment.startStudying())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void complete(){
        Enrollment enrollment = EnrollmentFixture.createEnrollment();
        enrollment.startStudying();
        enrollment.complete();

        Assertions.assertThat(enrollment.getStatus()).isEqualTo(EnrollmentStatus.COMPLETED);
        Assertions.assertThat(enrollment.getCompletedAt()).isNotNull();

        assertThatThrownBy(() -> enrollment.complete())
                .isInstanceOf(IllegalStateException.class);
    }
}