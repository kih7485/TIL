package kih.splearn.application.course.provided;

import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.support.stereotype.ApplicationServiceTest;
import kih.splearn.support.test.BaseApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@ApplicationServiceTest
@RequiredArgsConstructor
class CourseCreatorTest extends BaseApplicationServiceTest {
    final CourseCreator courseCreator;

    @Test
    void create(){
        prepareInstructor();

        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        Assertions.assertThat(course.getId()).isNotNull();
    }

    @Test
    void updateInfo(){
        prepareInstructor();

        Course course = courseCreator.create(CourseFixture.createCourseCreateRequest(instructor.getId(), null));

        Course updated = courseCreator.updateInfo(course.getId(), CourseFixture.createCourseInfoUpdateRequest("Updated"));

        Assertions.assertThat(updated.getTitle()).isEqualTo("Updated");
    }
}