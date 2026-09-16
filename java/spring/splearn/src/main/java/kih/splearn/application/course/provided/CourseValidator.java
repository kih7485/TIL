package kih.splearn.application.course.provided;

import kih.splearn.domain.course.Course;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.support.exception.ValidationException;

public interface CourseValidator {
    void validateForCreate(Instructor instructor, CourseCreateRequest createRequest) throws ValidationException;

    void validateForUpdate(Course course, CourseInfoUpdateRequest infoUpdateRequest)throws ValidationException;

    void validateForReview(Course course) throws ValidationException;

    void validateForPublish(Course course) throws ValidationException;

    void validateForArchive(Course course) throws ValidationException;
}
