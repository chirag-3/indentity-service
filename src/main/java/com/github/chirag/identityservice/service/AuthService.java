package com.github.chirag.identityservice.service;
import com.github.chirag.identityservice.entity.User;

public interface AuthService {
    public User getCaller(String authorization);
}
