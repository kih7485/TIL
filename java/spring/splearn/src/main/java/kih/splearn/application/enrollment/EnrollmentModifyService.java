package kih.splearn.application.enrollment;

import kih.splearn.application.course.provided.CourseFinder;
import kih.splearn.application.enrollment.provided.EnrollRequest;
import kih.splearn.application.enrollment.provided.Enroller;
import kih.splearn.application.enrollment.provided.EnrollmentFinder;
import kih.splearn.application.enrollment.required.EnrollmentRepository;
import kih.splearn.application.member.provided.MemberFinder;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.enrollment.Enrollment;
import kih.splearn.domain.member.Member;
import kih.splearn.support.stereotype.ApplicationService;
import kih.splearn.support.stereotype.ValidatedApplicationService;
import lombok.RequiredArgsConstructor;

@ValidatedApplicationService
@RequiredArgsConstructor
public class EnrollmentModifyService implements Enroller {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentFinder enrollmentFinder;
    private final MemberFinder memberFinder;
    private final CourseFinder courseFinder;

    @Override
    public Enrollment enroll(EnrollRequest enrollRequest) {
        Member member = memberFinder.find(enrollRequest.memberId());
        Course course = courseFinder.finder(enrollRequest.courseId());

        checkDuplication(member, course);

        Enrollment enrollment = Enrollment.enroll(member, course);
        return enrollmentRepository.save(enrollment);
    }

    private void checkDuplication(Member member, Course course) {
        if(enrollmentRepository.findByMemberIdAndCourseId(member.getId(), course.getId()).isPresent()){
            throw new IllegalArgumentException("이미 수강중인 강의입니다.");
        }
    }

    @Override
    public Enrollment startStudying(Long enrollmentId) {
        Enrollment enrollment = enrollmentFinder.find(enrollmentId);

        enrollment.startStudying();

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public Enrollment complete(Long enrollmentId) {
        Enrollment enrollment = enrollmentFinder.find(enrollmentId);

        enrollment.complete();

        return enrollmentRepository.save(enrollment);
    }
}
