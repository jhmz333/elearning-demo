package com.jhmarquez.training.elearning.usersms.services;

import com.jhmarquez.training.elearning.usersms.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> getById(Long id);

    Optional<User> getByEmail(String email);

    User save(User user);

    void delete(Long id);

    List<User> findAllById(Iterable<Long> ids);
}
