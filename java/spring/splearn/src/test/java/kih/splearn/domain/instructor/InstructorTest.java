package kih.splearn.domain.instructor;

import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InstructorTest {

    @Test
    void apply(){
        Member member = MemberFixture.createActiveMember();

        Instructor instructor = Instructor.apply(member);

        Assertions.assertThat(instructor.getMember()).isEqualTo(member);
        Assertions.assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.PENDING);
    }

    @Test
    void applyFailedMemberNotActive(){
        Member member = MemberFixture.createMember(); //PENDING

        Assertions.assertThatThrownBy(() -> Instructor.apply(member))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void approve(){
        Instructor instructor = InstructorFixture.createInstructor();
        instructor.approve();

        Assertions.assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.ACTIVE);
    }

    @Test
    void approveFailed(){
        Instructor instructor = InstructorFixture.createActiveInstructor();

        Assertions.assertThatThrownBy(() -> instructor.approve())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void reject(){
        Instructor instructor = InstructorFixture.createInstructor();
        instructor.reject();

        Assertions.assertThat(instructor.getStatus()).isEqualTo(InstructorStatus.REJECTED);
    }

    @Test
    void rejectFailed(){
        Instructor instructor = InstructorFixture.createInstructor();
        instructor.reject();

        Assertions.assertThatThrownBy(() -> instructor.reject())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void isActive(){
        Instructor instructor = InstructorFixture.createInstructor();

        Assertions.assertThat(instructor.isActive()).isFalse();

        instructor.approve();;
        Assertions.assertThat(instructor.isActive()).isTrue();
    }

    @Test
    void ensureActive(){
        Instructor instructor = InstructorFixture.createInstructor();

        Assertions.assertThatThrownBy(() -> instructor.ensureActive())
                .isInstanceOf(IllegalStateException.class);

        instructor.approve();
        instructor.ensureActive();
    }
}