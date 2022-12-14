package com.jhmarquez.training.elearning.coursesms.repositories;

import com.jhmarquez.training.elearning.coursesms.models.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("delete from UserCourse uc where uc.userId=?1")
    void deleteUserCourseById(Long id);
}
