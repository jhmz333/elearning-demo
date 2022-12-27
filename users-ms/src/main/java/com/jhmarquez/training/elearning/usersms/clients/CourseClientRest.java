package com.jhmarquez.training.elearning.usersms.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-ms")
public interface CourseClientRest {

    @DeleteMapping("/delete-user/{id}")
    void deleteUserCourseById(@PathVariable Long id);
}
