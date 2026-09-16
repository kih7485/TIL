package kih.splearn.support.test;

import kih.splearn.application.course.provided.CourseCreator;
import kih.splearn.application.instructor.provided.InstructorApplication;
import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.instructor.InstructorFixture;
import kih.splearn.domain.member.Member;
import kih.splearn.support.stereotype.ApplicationService;
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

    protected Member member;
    protected Instructor instructor;
    protected Course course;

    @NonNull
    protected Instructor prepareInstructor() {
        prepareMember();
        instructor = instructorApplication.apply(InstructorFixture.createApplyRequest(member));
        instructor.approve();
        return instructor;
    }

    @NonNull
    protected Member prepareMember() {
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
}
