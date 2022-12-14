package com.jhmarquez.training.elearning.coursesms.services;

import com.jhmarquez.training.elearning.coursesms.models.User;
import com.jhmarquez.training.elearning.coursesms.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();

    Optional<Course> findById(Long id);

    Course save(Course course);

    void delete(Long id);

    void deleteUserCourseById(Long id);

    Optional<User> addUserToCourse(User user, Long courseId);

    Optional<User> createUser(User user, Long courseId);

    Optional<User> removeUserFromCourse(User user, Long courseId);

}
