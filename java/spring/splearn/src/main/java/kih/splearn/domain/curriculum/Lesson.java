package kih.splearn.domain.curriculum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import kih.splearn.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true, exclude = {"section"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lesson extends AbstractEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    Section section;

    @Column(length = 200)
    String title;

    Lesson(Section section, String title) {
        this.section = section;
        this.title = Objects.requireNonNull(title);
    }

    void updateTitle(String title) {
        this.title = Objects.requireNonNull(title);
    }

    public void moveTo(Section section) {
        this.section = section;
    }
}
