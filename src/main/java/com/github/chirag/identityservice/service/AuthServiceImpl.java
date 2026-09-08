package com.github.chirag.identityservice.service;

import com.github.chirag.identityservice.entity.User;
import com.github.chirag.identityservice.exception.UserNotAuthorizedException;
import com.github.chirag.identityservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    MessageSource messageSource;

    @Autowired
    UserRepository userRepository;

    public User getCaller(String authorization) {
      if(authorization.startsWith("Basic")) {
          return extractUserFromBasicAuth(authorization.substring(6));
      } else if(authorization.startsWith("Bearer")) {
          return extractUserFromBearerToken(authorization.substring(7));
      }

      throw new UserNotAuthorizedException(messageSource.getMessage("exception.user.invalid", null,
              LocaleContextHolder.getLocale()));
    }

    private User extractUserFromBearerToken(String authorization) {
        return null;
    }

    private User extractUserFromBasicAuth(String authorization) {
        byte[] decodedAuth = Base64.getDecoder().decode(authorization);
        String credentials = new String(decodedAuth, StandardCharsets.UTF_8);
        String[] values = credentials.split(":", 2);
        String email = values[0];
        String password = values[1];
         if (email.isBlank() || password.isBlank()) {
            throw new UserNotAuthorizedException(messageSource.getMessage("exception.user.invalid", null,
                    LocaleContextHolder.getLocale()));
        }


        User caller = userRepository.getUserIdByEmail(email);

         if(caller==null) {
             throw new UserNotAuthorizedException(messageSource.getMessage("exception.user.invalid", null,
                     LocaleContextHolder.getLocale()));
         }

         if(!isPasswordCorrect(caller, password)) {
             throw new UserNotAuthorizedException(messageSource.getMessage("exception.user.invalid", null,
                     LocaleContextHolder.getLocale()));
         }

         return caller;

    }

    private boolean isPasswordCorrect(User caller, String password) {
        try {
            byte[] saltFromDb = Base64.getDecoder().decode(caller.getPasswordSalt());
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltFromDb, 600_000, 256);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] computedHash = skf.generateSecret(spec).getEncoded();
            byte[] storedHashBytes = Base64.getDecoder().decode(caller.getHashedPassword());
            return MessageDigest.isEqual(computedHash, storedHashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
