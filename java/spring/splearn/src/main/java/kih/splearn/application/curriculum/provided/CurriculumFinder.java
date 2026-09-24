package kih.splearn.application.curriculum.provided;

import kih.splearn.domain.curriculum.Curriculum;
import kih.splearn.domain.curriculum.Lesson;

import java.util.Optional;

public interface CurriculumFinder {
    Curriculum find(Long curriculumId);

    Curriculum findWithSections(Long curriculumId);

    Curriculum findByCourse(Long courseId);

    Optional<Lesson> firstLesson(Long curriculumId);

    Optional<Lesson> nextLesson(Long curriculumId, Long lessonId);
}
