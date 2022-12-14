package com.jhmarquez.training.elearning.usersms.services;

import com.jhmarquez.training.elearning.usersms.clients.CourseClientRest;
import com.jhmarquez.training.elearning.usersms.models.entities.User;
import com.jhmarquez.training.elearning.usersms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceBase implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CourseClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
        client.deleteUserCourseById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllById(Iterable<Long> ids) {
        return (List<User>) repository.findAllById(ids);
    }
}
