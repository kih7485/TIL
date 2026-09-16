package kih.splearn.domain.course;

import jakarta.validation.Valid;
import kih.splearn.application.course.provided.CourseCreateRequest;
import kih.splearn.application.course.provided.CourseInfoUpdateRequest;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.instructor.InstructorFixture;
import org.instancio.Instancio;
import org.instancio.Select;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

public class CourseFixture {
    public static Course createCourse(@Nullable Instructor instructor, @Nullable String title) {
        CourseDetail detail = Instancio.of(CourseDetail.class)
                .ignore(Select.field(CourseDetail::getId))
                .generate(Select.field(CourseDetail::getDescription), gen -> gen.string().maxLength(500).nullable())
                .set(Select.field(CourseDetail::getCreatedAt), LocalDateTime.now())
                .create();

        return Instancio.of(Course.class)
                .ignore(Select.field(Course::getId))
                .set(Select.field(Course::getInstructor),
                        instructor == null ? InstructorFixture.createActiveInstructor() : instructor)
                .set(Select.field(Course::getTitle),
                        title == null ? Instancio.gen().string().maxLength(100).minLength(2).get() : title)
                .set(Select.field(Course::getStatus), CourseStatus.DRAFT)
                .set(Select.field(Course::getDetail), detail)
                .create();
    }

    public static Course createCourse(){
        return createCourse(null, null);
    }

    public static CourseCreateRequest createCourseCreateRequest(Long instructorId, @Nullable String title) {
        return Instancio.of(CourseCreateRequest.class)
                .set(Select.field(CourseCreateRequest::instructorId), instructorId)
                .set(Select.field(CourseCreateRequest::title),
                        title == null ? Instancio.gen().string().maxLength(100).minLength(2).get() : title)
                .generate(Select.field(CourseCreateRequest::description), gen -> gen.string().maxLength(500).nullable())
                .create();
    }

    public static CourseInfoUpdateRequest createCourseInfoUpdateRequest(String title) {
        return Instancio.of(CourseInfoUpdateRequest.class)
                .set(Select.field(CourseInfoUpdateRequest::title),
                        title == null ? Instancio.gen().string().maxLength(100).minLength(2).get() : title)
                .generate(Select.field(CourseInfoUpdateRequest::description), gen -> gen.string().maxLength(500))
                .create();
    }

    public static Course createPublishedCourse() {
        Course course = createCourse();
        course.updateInfo(createCourseInfoUpdateRequest(course.getTitle()).toInfo());
        course.submitForReview();
        course.publish();
        return course;
    }
}
