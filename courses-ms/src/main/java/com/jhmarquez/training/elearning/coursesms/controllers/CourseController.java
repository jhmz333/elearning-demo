package com.jhmarquez.training.elearning.coursesms.controllers;

import com.jhmarquez.training.elearning.coursesms.models.User;
import com.jhmarquez.training.elearning.coursesms.models.entities.Course;
import com.jhmarquez.training.elearning.coursesms.services.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    @Autowired
    private CourseService service;

    @GetMapping("/")
    public List<Course> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id) {
        Optional<Course> course = service.findById(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@Valid @RequestBody Course course, BindingResult result) {

        if (course.getId() != null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("id", "The identifier must not be informed in the body of the request."));
        }

        if (result.hasErrors()) {
            return validateCourse(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {

        if (course.getId() != null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("id", "The identifier must not be informed in the body of the request."));
        }

        if (result.hasErrors()) {
            return validateCourse(result);
        }

        Optional<Course> optionalCourse = service.findById(id);
        if (optionalCourse.isPresent()) {
            Course currentCourse = optionalCourse.get();
            currentCourse.setName(course.getName());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(currentCourse));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Course> optionalCourse = service.findById(id);
        if (optionalCourse.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/add-user/{courseId}")
    public ResponseEntity<?> addCourseToCourse(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userAdded;
        try {
            userAdded = service.addUserToCourse(user, courseId);
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Error getting the user from the User Microservice: "
                            + exception.getMessage()));
        }

        if (userAdded.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userAdded.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userCreated;
        try {
            userCreated = service.createUser(user, courseId);
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Error saving the user into the User Microservice: "
                            + exception.getMessage()));
        }

        if (userCreated.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remove-user/{courseId}")
    public ResponseEntity<?> removeUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> userCreated;
        try {
            userCreated = service.removeUserFromCourse(user, courseId);
        } catch (FeignException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Collections.singletonMap("errorMessage", "Error getting the user from the User Microservice: "
                            + exception.getMessage()));
        }

        if (userCreated.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userCreated.get());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUserCourseById(@PathVariable Long id) {
        service.deleteUserCourseById(id);
        return ResponseEntity.noContent().build();
    }

    private static ResponseEntity<Map<String, String>> validateCourse(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
