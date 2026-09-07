package com.github.chirag.identityservice.controller;

import com.github.chirag.identityservice.entity.User;
import com.github.chirag.identityservice.service.AuthService;
import com.github.chirag.identityservice.service.UserService;
import com.github.chirag.identityservice.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<String> createUser(@RequestHeader("Authorization") String authorization, @RequestBody User body) {
        try {
            User caller = authService.getCaller(authorization);
            if (!userService.isSuperUser(caller)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user is not authorized to perform this action");
            }
            User user = userService.createUser(body);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
