package kih.splearn.domain.curriculum;

import kih.splearn.domain.course.CourseFixture;

public class CurriculumFixture {
    public static Curriculum createCurriculum(){
        return new Curriculum(CourseFixture.createCourse());
    }
}
