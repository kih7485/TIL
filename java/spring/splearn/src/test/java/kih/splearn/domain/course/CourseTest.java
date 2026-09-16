package kih.splearn.domain.course;

import kih.splearn.domain.instructor.InstructorFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    Course course;

    @BeforeEach
    void setUp(){
        this.course = CourseFixture.createCourse();
        this.course.updateInfo(new CourseUpdateInfo(course.getTitle(), "desc"));
    }

    @Test
    void create(){
        var instructor = InstructorFixture.createActiveInstructor();

        Course course = new Course(instructor, "Clean Spring 2", "desc");

        Assertions.assertThat(course.getInstructor()).isEqualTo(instructor);
        Assertions.assertThat(course.getTitle()).isEqualTo("Clean Spring 2");
        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.DRAFT);
        Assertions.assertThat(course.getDetail().getDescription()).isEqualTo("desc");
    }

    @Test
    void createFailNotActiveInstructor(){
        var instructor = InstructorFixture.createInstructor();

        assertThatThrownBy(() -> new Course(instructor, "title", null))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void submitForReview(){
        course.submitForReview();

        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);
        
        assertThatThrownBy(() -> course.submitForReview())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void publish(){
        course.submitForReview();
        course.publish();

        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
        Assertions.assertThat(course.getDetail().getPublishedAt()).isNotNull();

        assertThatThrownBy(() -> course.publish())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void archive(){
        course.submitForReview();
        course.publish();

        course.archive();

        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
        Assertions.assertThat(course.getDetail().getArchivedAt()).isNotNull();

        assertThatThrownBy(() -> course.archive())
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void updateInfo(){
        course.updateInfo(new CourseUpdateInfo("Clean Spring 3", "updated desc"));

        Assertions.assertThat(course.getTitle()).isEqualTo("Clean Spring 3");
        Assertions.assertThat(course.getDetail().getDescription()).isEqualTo("updated desc");
    }
}