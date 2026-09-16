package kih.splearn.application.instructor;


import kih.splearn.application.instructor.provided.DuplicateInstructorApplicationException;
import kih.splearn.application.instructor.provided.InstructorApplication;
import kih.splearn.application.instructor.provided.InstructorApplyRequest;
import kih.splearn.application.instructor.provided.InstructorFinder;
import kih.splearn.application.instructor.required.InstructorRepository;
import kih.splearn.application.member.provided.MemberFinder;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.member.Member;
import kih.splearn.support.stereotype.ValidatedApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@ValidatedApplicationService
@RequiredArgsConstructor
public class InstructorModifyService implements InstructorApplication {
    private final InstructorRepository instructorRepository;
    private final MemberFinder memberFinder;
    private final InstructorFinder instructorFinder;

    @Override
    public Instructor apply(InstructorApplyRequest applyRequest) {
        //member 를 찾아오고
        Member member = memberFinder.find(applyRequest.memberId());

        checkDuplicateApplication(member);

        //instructor 를 만들고,
        Instructor instructor = Instructor.apply(member);

        //instructor 저장
        return instructorRepository.save(instructor);
    }

    private void checkDuplicateApplication(Member member) {
        if(instructorRepository.findByMemberId(member.getId()).isPresent()){
            throw new DuplicateInstructorApplicationException("회원은 중복해서 강사신청 할 수 없습니다.");
        };
    }

    @Override
    public Instructor approve(Long instructorId) {
        Instructor instructor = instructorFinder.find(instructorId);

        instructor.approve();

        return instructorRepository.save(instructor);
    }

    @Override
    public Instructor reject(Long instructorId) {
        Instructor instructor = instructorFinder.find(instructorId);

        instructor.reject();

        return instructorRepository.save(instructor);
    }
}
