package com.github.chirag.identityservice.repository;

import com.github.chirag.identityservice.entity.User;

public interface UserRepository {
    public User getUserIdByEmail(String email);
}
