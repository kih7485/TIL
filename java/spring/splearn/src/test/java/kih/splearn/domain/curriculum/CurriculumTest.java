package kih.splearn.domain.curriculum;

import kih.splearn.domain.course.CourseFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CurriculumTest {

    @Test
    void create(){
        var course = CourseFixture
                .createCourse();

        Curriculum curriculum = new Curriculum(course);

        Assertions.assertThat(curriculum.getCourse()).isEqualTo(course);
        
        assertThatThrownBy(() -> new Curriculum(null))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void addSection(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section section = curriculum.addSection("Section 1");

        Assertions.assertThat(curriculum.getSections()).containsExactly(section);
        Assertions.assertThat(curriculum.getSections()).extracting(Section::getTitle).containsExactly("Section 1");
    }

    @Test
    void addSectionWithIndex(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section section1 = curriculum.addSection("S1");
        Section section2 = curriculum.addSection("S2");

        Assertions.assertThat(curriculum.getSections()).containsExactly(section1, section2);

        Section section1_1 = curriculum.addSection(1, "S1_1");

        Assertions.assertThat(curriculum.getSections()).containsExactly(section1, section1_1, section2);

        Section section3 = curriculum.addSection(3,"S3");

        Assertions.assertThat(curriculum.getSections()).containsExactly(section1, section1_1, section2, section3);
        
        assertThatThrownBy(() -> curriculum.addSection(5,"S5"))
            .isInstanceOf(IndexOutOfBoundsException.class);

        assertThatThrownBy(() -> curriculum.addSection(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void addLesson(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        Lesson l0 = curriculum.addLesson(0, "L0");
        Lesson l1 = curriculum.addLesson(0, "L1");

        Assertions.assertThat(s0.getLessons()).containsExactly(l0, l1);

        Lesson l2 = curriculum.addLesson(1, "L2");

        Assertions.assertThat(s1.getLessons()).containsExactly(l2);

    }

    @Test
    void updateSectionTitle(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        curriculum.updateSectionTitle(0, "S0 Updated");

        Assertions.assertThat(s0.getTitle()).isEqualTo("S0 Updated");
    }


    @Test
    void updateLessonTitle(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        Lesson l0_0 = curriculum.addLesson(0, "L0");
        Lesson l0_1 = curriculum.addLesson(0, "L1");
        Lesson l1 = curriculum.addLesson(1, "L1");

        curriculum.updateLessonTitle(0, 0, "L0 Updated");

        Assertions.assertThat(l0_0.getTitle()).isEqualTo("L0 Updated");

        var lessons = curriculum.allLessons();
    }

    @Test
    void removeLesson(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        Lesson l0_0 = curriculum.addLesson(0, "L0_0");
        Lesson l0_1 = curriculum.addLesson(0, "L0_1");
        Lesson l1_0 = curriculum.addLesson(1, "L1_0");
        Lesson l1_1 = curriculum.addLesson(1, "L1_1");

        Assertions.assertThat(curriculum.allLessons()).extracting(Lesson::getTitle)
                .containsExactly("L0_0","L0_1","L1_0","L1_1");

        curriculum.removeLesson(0, 0);

        Assertions.assertThat(curriculum.allLessons()).extracting(Lesson::getTitle)
                .containsExactly("L0_1","L1_0","L1_1");

        curriculum.removeLesson(1, 1);

        Assertions.assertThat(curriculum.allLessons()).extracting(Lesson::getTitle)
                .containsExactly("L0_1","L1_0");
    }

    @Test
    void removeSection(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");
        Section s2 = curriculum.addSection("S2");

        Lesson l0_0 = curriculum.addLesson(0, "L0_0");
        Lesson l0_1 = curriculum.addLesson(0, "L0_1");
        Lesson l1_0 = curriculum.addLesson(1, "L1_0");
        Lesson l1_1 = curriculum.addLesson(1, "L1_1");
        Lesson l2_0 = curriculum.addLesson(2, "L2_0");
        Lesson l2_1 = curriculum.addLesson(2, "L2_1");

        Assertions.assertThat(SectionContent.from(curriculum)).containsExactly(
                new SectionContent("S0",
                        List.of(new LessonContent("L0_0"), new LessonContent("L0_1"))),
                new SectionContent("S1",
                        List.of(new LessonContent("L1_0"), new LessonContent("L1_1"))),
                new SectionContent("S2",
                        List.of(new LessonContent("L2_0"), new LessonContent("L2_1")))
        );

        curriculum.removeSection(2);

        Assertions.assertThat(SectionContent.from(curriculum)).containsExactly(
                new SectionContent("S0",
                        List.of(new LessonContent("L0_0"), new LessonContent("L0_1"))),
                new SectionContent("S1",
                        List.of(new LessonContent("L1_0"), new LessonContent("L1_1"), new LessonContent("L2_0"), new LessonContent("L2_1")))
        );

        curriculum.removeSection(0);

        Assertions.assertThat(SectionContent.from(curriculum)).containsExactly(
                new SectionContent("S1",
                        List.of(new LessonContent("L0_0"), new LessonContent("L0_1"),
                                new LessonContent("L1_0"), new LessonContent("L1_1"),
                                new LessonContent("L2_0"), new LessonContent("L2_1")))
        );
        
        assertThatThrownBy(() -> curriculum.removeSection(0))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void moveLesson(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        Lesson l0_0 = curriculum.addLesson(0, "L0_0");
        Lesson l0_1 = curriculum.addLesson(0, "L0_1");
        Lesson l0_2 = curriculum.addLesson(0, "L0_2");
        Lesson l1_0 = curriculum.addLesson(1, "L1_0");
        Lesson l1_1 = curriculum.addLesson(1, "L1_1");
        Lesson l1_2 = curriculum.addLesson(1, "L1_2");


        curriculum.moveLesson(0, 0, 0, 1);

        Assertions.assertThat(SectionContent.from(curriculum)).containsExactly(
                new SectionContent("S0",
                        List.of(new LessonContent("L0_1"), new LessonContent("L0_0"), new LessonContent("L0_2"))),
                new SectionContent("S1",
                        List.of(new LessonContent("L1_0"), new LessonContent("L1_1"), new LessonContent("L1_2")))
        );

        curriculum.moveLesson(0, 2, 0, 0);

        Assertions.assertThat(SectionContent.from(curriculum)).containsExactly(
                new SectionContent("S0",
                        List.of(new LessonContent("L0_2"), new LessonContent("L0_1"), new LessonContent("L0_0"))),
                new SectionContent("S1",
                        List.of(new LessonContent("L1_0"), new LessonContent("L1_1"), new LessonContent("L1_2")))
        );
    }

    @Test
    void validate(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        curriculum.addSection("S0");
        curriculum.addLesson(0, "L0");

        curriculum.validate();

        Section s1 = curriculum.addSection("S1");
        curriculum.addLesson(1, "L1");

        curriculum.validate();

        curriculum.removeLesson(1, 0);
        
        assertThatThrownBy(curriculum::validate)
            .isInstanceOf(InvalidCurriculumException.class);
    }

    @Test
    void firstLesson(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();

        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        Assertions.assertThat(curriculum.firstLesson()).isEmpty();

        Lesson l0_0 = curriculum.addLesson(0, "L0_0");
        Lesson l0_1 = curriculum.addLesson(0, "L0_1");
        Lesson l0_2 = curriculum.addLesson(0, "L0_2");
        Lesson l1_0 = curriculum.addLesson(1, "L1_0");
        Lesson l1_1 = curriculum.addLesson(1, "L1_1");
        Lesson l1_2 = curriculum.addLesson(1, "L1_2");

        Assertions.assertThat(curriculum.firstLesson().orElseThrow()).isEqualTo(l0_0);

    }

    @Test
    void next(){
        Curriculum curriculum = CurriculumFixture.createCurriculum();
        Section s0 = curriculum.addSection("S0");
        Section s1 = curriculum.addSection("S1");

        Lesson l0_0 = curriculum.addLesson(0, "L0_0");
        Lesson l1_0 = curriculum.addLesson(1, "L1_0");
        Lesson l1_1 = curriculum.addLesson(1, "L1_1");

        Lesson lesson = curriculum.firstLesson().orElseThrow();

        lesson = curriculum.nextLesson(lesson).orElseThrow();
        Assertions.assertThat(lesson).isEqualTo(l1_0);

        lesson = curriculum.nextLesson(lesson).orElseThrow();
        Assertions.assertThat(lesson).isEqualTo(l1_1);
        Assertions.assertThat(curriculum.nextLesson(lesson)).isEmpty();

    }
}