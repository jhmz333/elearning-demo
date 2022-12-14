package com.jhmarquez.training.elearning.coursesms.services;

import com.jhmarquez.training.elearning.coursesms.clients.UserClientRest;
import com.jhmarquez.training.elearning.coursesms.models.User;
import com.jhmarquez.training.elearning.coursesms.models.entities.Course;
import com.jhmarquez.training.elearning.coursesms.models.entities.UserCourse;
import com.jhmarquez.training.elearning.coursesms.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceBase implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private UserClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        Optional<Course> course = repository.findById(id);
        if (course.isPresent()) {
            if (!course.get().getUserCourses().isEmpty()) {
                List<Long> userIds = course.get().getUserCourses().stream().map(UserCourse::getUserId).toList();
                course.get().setUsers(client.getAllByIds(userIds));
            }
            return course;
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserCourseById(Long id) {
        repository.deleteUserCourseById(id);
    }

    @Override
    @Transactional
    public Optional<User> addUserToCourse(User user, Long courseId) {

        Optional<Course> optionalCourse = repository.findById(courseId);
        if (optionalCourse.isPresent()) {
            User userMs = client.getUser(user.getId());

            Course course = optionalCourse.get();
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMs.getId());

            course.addUser(userCourse);
            repository.save(course);
            return Optional.of(userMs);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {

        Optional<Course> optionalCourse = repository.findById(courseId);
        if (optionalCourse.isPresent()) {
            User userCreatedMs = client.add(user);

            Course course = optionalCourse.get();
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userCreatedMs.getId());

            course.addUser(userCourse);
            repository.save(course);
            return Optional.of(userCreatedMs);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUserFromCourse(User user, Long courseId) {

        Optional<Course> optionalCourse = repository.findById(courseId);
        if (optionalCourse.isPresent()) {
            User userMs = client.getUser(user.getId());

            Course course = optionalCourse.get();
            UserCourse userCourse = new UserCourse();
            userCourse.setUserId(userMs.getId());

            course.removeUser(userCourse);
            repository.save(course);
            return Optional.of(userMs);
        }

        return Optional.empty();
    }
}
