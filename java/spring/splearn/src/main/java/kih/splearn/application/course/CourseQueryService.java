package kih.splearn.application.course;

import kih.splearn.application.course.provided.CourseFinder;
import kih.splearn.application.course.required.CourseRepository;
import kih.splearn.domain.course.Course;
import kih.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationService
@RequiredArgsConstructor
public class CourseQueryService implements CourseFinder {
    private final CourseRepository courseRepository;

    @Override
    public Course finder(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(
                () -> new IllegalArgumentException("강의를 찾을 수 없습니다. ID: " + courseId)
        );
    }

    @Override
    public List<Course> findByTitle(String keyword) {
        return courseRepository.findByTitleContaining(keyword);
    }

    @Override
    public List<Course> findByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
}
