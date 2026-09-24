package kih.splearn.application.curriculum.required;

import kih.splearn.domain.curriculum.Lesson;
import org.springframework.data.repository.Repository;

public interface LessonRepository extends Repository<Lesson, Long> {
    void delete(Lesson lesson);
}
