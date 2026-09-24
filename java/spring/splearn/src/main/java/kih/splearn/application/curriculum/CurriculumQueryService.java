package kih.splearn.application.curriculum;

import kih.splearn.application.curriculum.provided.CurriculumFinder;
import kih.splearn.application.curriculum.required.CurriculumRepository;
import kih.splearn.domain.curriculum.Curriculum;
import kih.splearn.domain.curriculum.Lesson;
import kih.splearn.support.stereotype.ApplicationService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@ApplicationService
@RequiredArgsConstructor
public class CurriculumQueryService implements CurriculumFinder {
    private final CurriculumRepository curriculumRepository;

    @Override
    public Curriculum find(Long curriculumId) {
        return curriculumRepository.findById(curriculumId).orElseThrow(
                () -> new IllegalArgumentException("커리큘럼을 찾을 수 없습니다.")
        );
    }

    @Override
    public Curriculum findByCourse(Long courseId) {
        return curriculumRepository.findByCourseId(courseId).orElseThrow(
                () -> new IllegalArgumentException("커리큘럼을 찾을 수 없습니다. courseId: " + courseId)
        );
    }

    @Override
    public Curriculum findWithSections(Long curriculumId) {
        return curriculumRepository.findWithSectionsById(curriculumId).orElseThrow(
                () -> new IllegalArgumentException("커리큘럼을 찾을 수 없습니다.")
        );
    }

    @Override
    public Optional<Lesson> firstLesson(Long curriculumId) {
        return findWithSections(curriculumId).firstLesson();
    }

    @Override
    public Optional<Lesson> nextLesson(Long curriculumId, Long lessonId) {
        return findWithSections(curriculumId).nextLesson(lessonId);
    }
}
