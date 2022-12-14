package com.jhmarquez.training.elearning.coursesms.models.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_course")
public class UserCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private Long userId;

    @Override
    public boolean equals(Object user) {
        if (!(user instanceof UserCourse)) {
            return false;
        }
        return userId == ((UserCourse) user).userId;
    }

}
