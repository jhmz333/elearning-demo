package com.jhmarquez.training.elearning.usersms.repositories;

import com.jhmarquez.training.elearning.usersms.models.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
