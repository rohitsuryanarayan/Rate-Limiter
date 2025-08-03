package org.ratelimiter.controller;

import lombok.RequiredArgsConstructor;
import org.ratelimiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @GetMapping("/user")
    public String seeIfUserCanAccess() {
        userService.sampleMethod();
        return "/user accessed";
    }
}
