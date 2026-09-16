package kih.splearn.domain.instructor;

import jakarta.validation.Valid;
import kih.splearn.application.instructor.provided.InstructorApplyRequest;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.member.Member;

public class InstructorFixture {
    public static Instructor createInstructor(Member member) {
        return Instructor.apply(member);
    }

    public static Instructor createInstructor() {
        return createInstructor(MemberFixture.createActiveMember());
    }

    public static Instructor createActiveInstructor() {
        Instructor instructor = createInstructor();
        instructor.approve();
        return instructor;
    }

    public static @Valid InstructorApplyRequest createApplyRequest(Member member) {
        return new InstructorApplyRequest(member.getId());
    }

    public static Instructor createActiveInstructor(Member member) {
        Instructor instructor = createInstructor(member);
        instructor.approve();
        return instructor;
    }
}
