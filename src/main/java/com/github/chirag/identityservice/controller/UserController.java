package com.github.chirag.identityservice.controller;

import com.github.chirag.identityservice.entity.User;
import com.github.chirag.identityservice.service.AuthService;
import com.github.chirag.identityservice.service.UserService;
import com.github.chirag.identityservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<Object> createUser(@RequestHeader("Authorization") String authorization, @RequestBody User body) {
        User caller = authService.getCaller(authorization);
        User user = userService.createUser(body, caller);
        return ResponseEntity.ok(new UserDto(user.getPermalink(), user.getEmail()));
    }

}
