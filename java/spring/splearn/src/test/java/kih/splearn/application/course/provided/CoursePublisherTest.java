package kih.splearn.application.course.provided;

import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseStatus;
import kih.splearn.support.stereotype.ApplicationServiceTest;
import kih.splearn.support.test.BaseApplicationServiceTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@ApplicationServiceTest
@RequiredArgsConstructor
class CoursePublisherTest extends BaseApplicationServiceTest {
    final CoursePublisher coursePublisher;

    @BeforeEach
    void setUp() {
        prepareCourse();
    }

    @Test
    void submitForReview() {
        Course courseForReview = coursePublisher.submitForReview(course.getId());

        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.IN_REVIEW);
    }

    @Test
    void publish() {
        coursePublisher.submitForReview(course.getId());
        Course courseForPublish = coursePublisher.publish(course.getId());

        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.PUBLISHED);
    }

    @Test
    void archive() {
        coursePublisher.submitForReview(course.getId());
        coursePublisher.publish(course.getId());
        Course courseForArchive = coursePublisher.archive(course.getId());

        Assertions.assertThat(course.getStatus()).isEqualTo(CourseStatus.ARCHIVED);
    }
}