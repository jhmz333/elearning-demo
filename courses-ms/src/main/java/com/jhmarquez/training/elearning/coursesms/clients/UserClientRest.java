package com.jhmarquez.training.elearning.coursesms.clients;

import com.jhmarquez.training.elearning.coursesms.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "users-ms")
public interface UserClientRest {

    @GetMapping("/{id}")
    User getUser(@PathVariable Long id);

    @PostMapping("/")
    User add(@RequestBody User user);

    @GetMapping("/users")
    List<User> getAllByIds(@RequestParam Iterable<Long> ids);
}
