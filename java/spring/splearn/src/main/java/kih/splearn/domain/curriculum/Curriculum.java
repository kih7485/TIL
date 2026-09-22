package kih.splearn.domain.curriculum;

import jakarta.persistence.*;
import kih.splearn.domain.AbstractEntity;
import kih.splearn.domain.course.Course;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Curriculum extends AbstractEntity {
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private Course course;

    @OneToMany(mappedBy = "curriculum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Section> sections = new ArrayList<>();

    Curriculum(Course course) {
        this.course = Objects.requireNonNull(course);
    }

    Section addSection(String title) {
        Section section = new Section(this, title);

        this.sections.add(section);

        return section;
    }

    Section addSection(int sectionIndex, String title) {
        Objects.checkIndex(sectionIndex, sections.size() + 1);

        Section section = new Section(this, title);

        this.sections.add(sectionIndex, section);

        return section;
    }

    Lesson addLesson(int sectionIndex, String title) {
        return this.sections.get(sectionIndex).addLesson(title);
    }

    Section updateSectionTitle(int sectionIndex, String title) {
        Section section = this.sections.get(sectionIndex);

        section.updateTitle(title);

        return section;
    }

    void updateLessonTitle(int sectionIndex, int lessonIndex, String title) {
        this.sections.get(sectionIndex).updateLessonTitle(lessonIndex, title);
    }

    public void removeLesson(int sectionIndex, int lessonIndex) {
        this.sections.get(sectionIndex).removeLesson(lessonIndex);
    }

    public List<Lesson> allLessons(){
        return this.sections.stream().flatMap(section -> section.getLessons().stream())
                .toList();
    }

    public void removeSection(int sectionIndex) {
        Assert.state(this.sections.size() >1, "마지막 남은 섹션은 제외할 수 없습니다.");
        Section removed = this.sections.remove(sectionIndex);

        if (sectionIndex == 0) {
            Section next = this.sections.getFirst();
            removed.moveAllLessonsTo(next, 0);
        }else {
            Section previous = this.sections.get(sectionIndex - 1);
            removed.moveAllLessonsTo(previous, previous.getLessons().size());
        }
    }

    public void moveLesson(int fromSectionIndex, int fromLessonIndex, int toSectionIndex, int toLessonIndex) {
        Section from = this.sections.get(fromSectionIndex);
        Section to = this.sections.get(toSectionIndex);

        to.addLesson(toLessonIndex, from.removeLesson(fromLessonIndex));
    }

    public void validate() {
    }
}
