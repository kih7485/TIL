package kih.splearn.support.test;

import jakarta.persistence.EntityManager;
import kih.splearn.application.course.required.CourseRepository;
import kih.splearn.application.enrollment.required.EnrollmentRepository;
import kih.splearn.application.instructor.required.InstructorRepository;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.domain.enrollment.Enrollment;
import kih.splearn.domain.enrollment.EnrollmentFixture;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.instructor.InstructorFixture;
import kih.splearn.domain.member.Member;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
public class BaseRepositoryTest {
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    EnrollmentRepository enrollmentRepository;

    @Autowired
    CourseRepository courseRepository;

    protected Member member;

    protected Instructor instructor;

    protected Course course;

    protected Enrollment enrollment;

    protected Course preparePublishedCourse() {
        prepareCourse();
        course.submitForReview();
        course.publish();

        return course;
    }
    protected Course prepareCourse(){
        return prepareCourse(null, null);
    }

    protected Course prepareCourse(@Nullable Instructor instructor, @Nullable String title) {
        if(instructor == null) prepareActiveInstructor();
        course = courseRepository.save(CourseFixture.createCourse(instructor == null ? this.instructor : instructor, title));
        course.updateInfo(CourseFixture.createCourseInfoUpdateRequest(title).toInfo());

        return course;
    }
    protected Instructor prepareActiveInstructor() {
        prepareActiveMember();
        instructor = instructorRepository.save(InstructorFixture.createActiveInstructor(member));
        return instructor;
    }

    protected Instructor prepareActiveInstructor(Member member) {
        instructor = instructorRepository.save(InstructorFixture.createActiveInstructor(member));
        return instructor;
    }

    protected Member prepareActiveMember() {
        member = memberRepository.save(MemberFixture.createActiveMember());
        return member;
    }

    protected Enrollment prepareEnrollment(Member member, Course course) {
        enrollment = enrollmentRepository.save(EnrollmentFixture.createEnrollment(member, course));
        return enrollment;
    }
}
