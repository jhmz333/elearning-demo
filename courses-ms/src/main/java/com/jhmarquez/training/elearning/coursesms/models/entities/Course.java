package com.jhmarquez.training.elearning.coursesms.models.entities;

import com.jhmarquez.training.elearning.coursesms.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<UserCourse> userCourses;

    @Transient
    private List<User> users;

    public Course(){
        userCourses = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addUser(UserCourse user) {
        userCourses.add(user);
    }

    public void removeUser(UserCourse user) {
        userCourses.remove(user);
    }
}
