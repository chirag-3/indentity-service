package com.github.chirag.identityservice.entity;

import lombok.Data;

@Data
public class User {
    private long id;
    private String permalink;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordSalt;
    private String hashedPassword;
    private boolean isSuperUser;
    private long lastPasswordResetDate;
    private boolean locked;
    private String resetToken;
    private long resetTokenExpiry;
    private long createdAt;
    private long updatedAt;
}
