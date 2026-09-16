package kih.splearn.application.course;

import kih.splearn.application.course.provided.*;
import kih.splearn.application.course.required.CourseRepository;
import kih.splearn.application.instructor.provided.InstructorFinder;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.support.stereotype.ApplicationService;
import kih.splearn.support.stereotype.ValidatedApplicationService;
import lombok.RequiredArgsConstructor;

@ValidatedApplicationService
@RequiredArgsConstructor
public class CourseModifyService implements CourseCreator, CoursePublisher {
    private final CourseRepository courseRepository;
    private final CourseFinder courseFinder;
    private final InstructorFinder instructorFinder;
    private final CourseValidator courseValidator;

    @Override
    public Course create(CourseCreateRequest createRequest) {
        //1. instructor 찾기
        Instructor instructor = instructorFinder.find(createRequest.instructorId());

        courseValidator.validateForCreate(instructor, createRequest);

        Course course = new Course(instructor, createRequest.title(), createRequest.description());

        //2. validate
        return courseRepository.save(course);
    }

    @Override
    public Course updateInfo(Long courseId, CourseInfoUpdateRequest infoUpdateRequest) {
        Course course = courseFinder.finder(courseId);

        courseValidator.validateForUpdate(course, infoUpdateRequest);

        course.updateInfo(infoUpdateRequest.toInfo());
        return courseRepository.save(course);
    }

    @Override
    public Course submitForReview(Long courseId) {
        Course course = courseFinder.finder(courseId);

        courseValidator.validateForReview(course);

        course.submitForReview();
        return courseRepository.save(course);
    }

    @Override
    public Course publish(Long courseId) {
        Course course = courseFinder.finder(courseId);

        courseValidator.validateForPublish(course);

        course.publish();
        return courseRepository.save(course);
    }

    @Override
    public Course archive(Long courseId) {
        Course course = courseFinder.finder(courseId);

        courseValidator.validateForArchive(course);

        course.archive();

        return courseRepository.save(course);
    }
}
