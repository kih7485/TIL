package kih.splearn.application.course.provided;

import kih.splearn.domain.course.Course;

import java.util.List;

public interface CourseFinder {
    Course finder(Long courseId);

    List<Course> findByTitle(String keyword);

    List<Course> findByInstructor(Long instructorId);
}
