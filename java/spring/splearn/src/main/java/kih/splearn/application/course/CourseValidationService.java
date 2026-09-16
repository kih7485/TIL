package kih.splearn.application.course;

import jakarta.validation.constraints.Size;
import kih.splearn.application.course.provided.CourseCreateRequest;
import kih.splearn.application.course.provided.CourseInfoUpdateRequest;
import kih.splearn.application.course.provided.CourseValidator;
import kih.splearn.application.course.required.CourseRepository;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.support.exception.ValidationException;
import kih.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CourseValidationService implements CourseValidator {
    private final CourseRepository courseRepository;

    @Override
    public void validateForCreate(Instructor instructor, CourseCreateRequest createRequest) throws ValidationException {
        instructor.ensureActive();

        List<String> errors = new ArrayList<>();

        checkTitleDuplicationForCreate(instructor, createRequest.title(), errors);
        checkBannedWords(createRequest.title(), errors);
        checkBannedWords(createRequest.description(), errors);

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateForUpdate(Course course, CourseInfoUpdateRequest updateRequest) throws ValidationException {
        List<String> errors = new ArrayList<>();

        checkTitleDuplicationForUpdate(course, course.getInstructor(), updateRequest.title(), errors);
        checkBannedWords(updateRequest.title(), errors);
        checkBannedWords(updateRequest.description(), errors);

        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }

    @Override
    public void validateForReview(Course course) throws ValidationException {

    }

    @Override
    public void validateForPublish(Course course) throws ValidationException {

    }

    @Override
    public void validateForArchive(Course course) throws ValidationException {

    }

    private void checkBannedWords(String text, List<String> errors) {
    }

    private void checkTitleDuplicationForCreate(Instructor instructor, String title, List<String> errors) {
        if(courseRepository.findByInstructorAndTitle(instructor, title).isPresent()){
            errors.add("이미 사용중인 강의 제목입니다. " + title);
        }
    }

    private void checkTitleDuplicationForUpdate(Course course, Instructor instructor, String title, List<String> errors) {
        courseRepository.findByInstructorAndTitle(instructor, title).ifPresent(found -> {
            if(!found.equals(course)){
                errors.add("이미 사용중인 강의 제목입니다. " + title);
            }
        });
    }
}
