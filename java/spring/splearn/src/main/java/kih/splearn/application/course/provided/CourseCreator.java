package kih.splearn.application.course.provided;

import jakarta.validation.Valid;
import kih.splearn.domain.course.Course;
import kih.splearn.support.exception.ValidationException;

/**
* 강의를 준비하는 작업
* */
public interface CourseCreator {
    Course create(@Valid CourseCreateRequest createRequest) throws ValidationException;

    Course updateInfo(Long courseId, @Valid CourseInfoUpdateRequest infoUpdateRequest) throws ValidationException;
}
