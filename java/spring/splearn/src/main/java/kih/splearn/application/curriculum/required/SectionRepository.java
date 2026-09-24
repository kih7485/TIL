package kih.splearn.application.curriculum.required;

import kih.splearn.domain.curriculum.Section;
import org.springframework.data.repository.Repository;

public interface SectionRepository extends Repository<Section, Long> {
    void delete(Section section);
}
