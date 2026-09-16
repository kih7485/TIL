package kih.splearn.application.instructor.required;

import kih.splearn.domain.instructor.Instructor;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface InstructorRepository extends Repository<Instructor, Long> {
    Instructor save(Instructor instructor);

    Optional<Instructor> findById(Long instructorId);

    Optional<Instructor> findByMemberId(Long memberId);
}
