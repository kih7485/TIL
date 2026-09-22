package kih.splearn.support.test;

import kih.splearn.application.course.provided.CourseCreator;
import kih.splearn.application.enrollment.provided.EnrollRequest;
import kih.splearn.application.enrollment.provided.Enroller;
import kih.splearn.application.instructor.provided.InstructorApplication;
import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.domain.enrollment.Enrollment;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.instructor.InstructorFixture;
import kih.splearn.domain.member.Member;
import kih.splearn.support.stereotype.ApplicationServiceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

@ApplicationServiceTest
public class BaseApplicationServiceTest {
    @Autowired
    MemberRegister memberRegister;
    @Autowired
    InstructorApplication instructorApplication;
    @Autowired
    CourseCreator courseCreator;
    @Autowired
    Enroller enroller;

    protected Member member;
    protected Instructor instructor;
    protected Course course;
    protected Enrollment enrollment;

    @NonNull
    protected Instructor prepareInstructor() {
        prepareActiveMember();
        instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        instructor.approve();
        return instructor;
    }

    @NonNull
    protected Member prepareActiveMember() {
        member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        member.activate();
        return member;
    }

    protected Course prepareCourse() {
        prepareInstructor();
        course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));
        course.updateInfo(CourseFixture.createCourseInfoUpdateRequest(null).toInfo());

        return course;
    }

    protected Course preparePublishedCourse(){
        prepareCourse();
        course.submitForReview();
        course.publish();

        return course;
    }

    protected Enrollment prepareEnrollment() {
        Member member = prepareActiveMember();
        Course course = preparePublishedCourse();
        enrollment = enroller.enroll(new EnrollRequest(member.getId(), course.getId()));
        return enrollment;
    }
}
