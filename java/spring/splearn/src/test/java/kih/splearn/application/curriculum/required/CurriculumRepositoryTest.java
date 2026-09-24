package kih.splearn.application.curriculum.required;

import kih.splearn.domain.curriculum.Curriculum;
import kih.splearn.support.test.BaseRepositoryTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.lang.NonNull;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@RequiredArgsConstructor
class CurriculumRepositoryTest extends BaseRepositoryTest {
    final CurriculumRepository curriculumRepository;

    @Test
    void saveAndFIndById(){
        Long curriculumId = saveCurriculum();

        Curriculum found = curriculumRepository.findById(curriculumId).orElseThrow();

        Assertions.assertThat(found.getId()).isEqualTo(curriculumId);
    }

    @Test
    void saveAndFindWithSectionsById(){
        Long curriculumId = saveCurriculum();

        Curriculum found = curriculumRepository.findWithSectionsById(curriculumId).orElseThrow();

        Assertions.assertThat(found.getId()).isEqualTo(curriculumId);
    }

    private Long saveCurriculum() {
        Curriculum curriculum = new Curriculum(prepareCourse());

        curriculum = curriculumRepository.save(curriculum);
        curriculum.addSection("S1");
        curriculum.addLesson(0, "L1");
        curriculum.addLesson(0, "L2");

        curriculum.addSection("S2");
        curriculum.addLesson(1, "L3");

        entityManager.flush();
        entityManager.clear();
        return curriculum.getId();
    }
}