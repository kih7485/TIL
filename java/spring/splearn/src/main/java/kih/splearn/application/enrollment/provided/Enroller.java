package kih.splearn.application.enrollment.provided;

import kih.splearn.domain.enrollment.Enrollment;

public interface Enroller {
    Enrollment enroll(Long memberId, Long courseId);

    Enrollment startStudying(Long enrollmentId);

    Enrollment complete(Long enrollmentId);
}
