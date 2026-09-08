package com.github.chirag.identityservice.service;

import com.github.chirag.identityservice.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User createUser(User body, User caller);
}
