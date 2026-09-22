package kih.splearn.application.course.required;

import jakarta.persistence.EntityManager;
import kih.splearn.application.instructor.required.InstructorRepository;
import kih.splearn.application.member.required.MemberRepository;
import kih.splearn.domain.MemberFixture;
import kih.splearn.domain.course.Course;
import kih.splearn.domain.course.CourseFixture;
import kih.splearn.domain.instructor.Instructor;
import kih.splearn.domain.instructor.InstructorFixture;
import kih.splearn.domain.member.Member;
import kih.splearn.support.test.BaseRepositoryTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor
class CourseRepositoryTest extends BaseRepositoryTest {
    final CourseRepository courseRepository;

    @BeforeEach
    void setup(){
        member = prepareActiveMember();
        instructor = prepareActiveInstructor(member);
    }

    @Test
    void saveAndFindId(){
        Course course = CourseFixture.createCourse(instructor, null);
        course = courseRepository.save(course);

        Assertions.assertThat(course.getId()).isNotNull();

        entityManager.flush();
        entityManager.clear();

        Course found = courseRepository.findById(course.getId()).orElseThrow();

        Assertions.assertThat(found).isEqualTo(course);
    }

    @Test
    void findByTitleContaining(){
        List<Long> ids = Stream.of(
                        prepareCourse(instructor, "Hello Spring"),
                        prepareCourse(instructor, "Clean Spring2"),
                        prepareCourse(instructor, "Clean Code"))
                .map(Course::getId).toList();

        Assertions.assertThat(courseRepository.findByTitleContaining("Spring").stream().map(Course::getId))
                .isEqualTo(List.of(ids.get(0), ids.get(1)));

        Assertions.assertThat(courseRepository.findByTitleContaining("Clean").stream().map(Course::getId))
                .isEqualTo(List.of(ids.get(1), ids.get(2)));

        Assertions.assertThat(courseRepository.findByTitleContaining("Code").stream().map(Course::getId))
                .isEqualTo(List.of(ids.get(2)));

        Assertions.assertThat(courseRepository.findByTitleContaining("JPA").stream().map(Course::getId))
                .isEqualTo(Collections.emptyList());
    }

    @Test
    void findByInstructor(){
        var instructor1 = prepareActiveInstructor();
        var instructor2 = prepareActiveInstructor();

        var course =  prepareCourse(instructor1, null);
        var course2 =  prepareCourse(instructor2, null);

        List<Course> courses = courseRepository.findByInstructorId(instructor1.getId());
        Assertions.assertThat(courses).singleElement().isEqualTo(course);

        List<Course> courses2 = courseRepository.findByInstructorId(instructor2.getId());
        Assertions.assertThat(courses2).singleElement().isEqualTo(course2);

        List<Course> courses2_1 = courseRepository.findByInstructor(instructor2);
        Assertions.assertThat(courses2_1).singleElement().isEqualTo(course2);
    }

    @Test
    void uniqueTitleInsturctor(){
        prepareCourse(instructor, "Title");
        
        assertThatThrownBy(() -> courseRepository.save(CourseFixture.createCourse(instructor, "Title")))
            .isInstanceOf(DataIntegrityViolationException.class);
    }
}