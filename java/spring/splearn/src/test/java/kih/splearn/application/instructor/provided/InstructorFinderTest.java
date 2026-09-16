package kih.splearn.application.instructor.provided;

import kih.splearn.application.member.provided.MemberRegister;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@RequiredArgsConstructor
class InstructorFinderTest {
    final InstructorFinder instructorFinder;
    final InstructorApplication instructorApplication;
    final MemberRegister memberRegister;

    @Test
    void findByMember(){
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        member = memberRegister.activate(member.getId());

        Instructor instructor = instructorApplication.apply(new InstructorApplyRequest(member.getId()));

        Instructor found = instructorFinder.findByMember(member.getId()).orElseThrow();

        Assertions.assertThat(instructor).isEqualTo(found);

        Assertions.assertThat(instructorFinder.findByMember(Long.MAX_VALUE).isPresent()).isFalse();
    }
}