package com.bizboar.superCoolBatchProgram.service;

import com.bizboar.superCoolBatchProgram.models.auth.User;
import com.bizboar.superCoolBatchProgram.repositories.UserRepository;
import com.bizboar.superCoolBatchProgram.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails =(UserDetailsImpl) authentication.getPrincipal();

        Optional<User> currentUser = userRepository.findById(userDetails.getId());

        if (currentUser.isEmpty()) {
            return null;
        }
        return currentUser.get();
    }
}