package kih.splearn.application.enrollment.provided;

import jakarta.validation.Valid;
import kih.splearn.domain.enrollment.Enrollment;

public interface Enroller {
    Enrollment enroll(@Valid EnrollRequest enrollRequest);

    Enrollment startStudying(Long enrollmentId);

    Enrollment complete(Long enrollmentId);
}
