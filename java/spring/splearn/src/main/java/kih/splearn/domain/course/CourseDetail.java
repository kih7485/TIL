package kih.splearn.domain.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import kih.splearn.domain.AbstractEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true, exclude = {})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseDetail extends AbstractEntity {
    @Column(length = 500)
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    private LocalDateTime archivedAt;

    CourseDetail(String description) {
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

    void publish() {
        this.publishedAt = LocalDateTime.now();
    }

    void archive() {
        this.archivedAt = LocalDateTime.now();
    }

    void updateInfo(CourseUpdateInfo updateInfo) {
        this.description = updateInfo.description();
    }
}
