package kih.splearn.application.course.provided;

import jakarta.validation.constraints.Size;
import org.jspecify.annotations.NonNull;

public record CourseCreateRequest(
        @NonNull Long instructorId,
        @Size(min = 2, max= 100) String title,
        @Size(max = 500) String description
) {
}
