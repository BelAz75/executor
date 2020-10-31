package com.virtuallab.user;

import com.virtuallab.util.rest.PageResponse;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public PageResponse<User> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return userService.findAll(page, pageSize);
    }
}
