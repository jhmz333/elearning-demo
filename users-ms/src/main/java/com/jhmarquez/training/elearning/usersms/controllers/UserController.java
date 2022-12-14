package com.jhmarquez.training.elearning.usersms.controllers;

import com.jhmarquez.training.elearning.usersms.models.entities.User;
import com.jhmarquez.training.elearning.usersms.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public List<User> getAll() {
        return service.findAll();
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        Optional<User> user = service.getById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> add(@Valid @RequestBody User user, BindingResult result) {

        if (user.getId() != null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("id", "The identifier must not be informed in the body of the request."));
        }

        if (result.hasErrors()) {
            return validateUser(result);
        }

        Optional<User> dbUser = service.getByEmail(user.getEmail());
        if (dbUser.isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("email", "The email has already registered."));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {

        if (user.getId() != null) {
            return ResponseEntity.badRequest().body(
                    Collections.singletonMap("id", "The identifier must not be informed in the body of the request."));
        }

        if (result.hasErrors()) {
            return validateUser(result);
        }

        Optional<User> userOptional = service.getById(id);
        if (userOptional.isPresent()) {

            Optional<User> otherUser = service.getByEmail(user.getEmail());
            if (otherUser.isPresent() && otherUser.get().getId() != id) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("email", "The email has already been registered to other user."));
            }

            User dbUser = userOptional.get();

            dbUser.setName(user.getName());
            dbUser.setEmail(user.getEmail());
            dbUser.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dbUser));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> userOptional = service.getById(id);
        if (userOptional.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validateUser(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
