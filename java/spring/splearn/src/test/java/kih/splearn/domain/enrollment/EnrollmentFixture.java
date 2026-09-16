package kih.splearn.domain.enrollment;

import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.domain.member.Member;
import org.jspecify.annotations.Nullable;

public class EnrollmentFixture {
    public static Enrollment createEnrollment(@Nullable Member member, @Nullable Course course) {
        return Enrollment.enroll(
                member == null ? MemberFixture.createActiveMember() : member,
                course == null ? CourseFixture.createPublishedCourse() : course
        );
    }

    public static Enrollment createEnrollment() {
        return createEnrollment(
                null,
                null
        );
    }
}
