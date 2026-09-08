package com.github.chirag.identityservice.service;

import com.github.chirag.identityservice.entity.User;
import com.github.chirag.identityservice.exception.ActionForbiddenForUserException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class UserServiceImpl implements UserService {

    MessageSource messageSource;

    @Override
    public User createUser(User user, User caller) {
        if(!caller.isSuperUser()) {
            throw new ActionForbiddenForUserException(messageSource.getMessage("exception.user.not-allowed",
                    null,
                    LocaleContextHolder.getLocale()));
        }
    }
}
