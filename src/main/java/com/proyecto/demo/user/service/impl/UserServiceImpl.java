package com.proyecto.demo.user.service.impl;

import com.proyecto.demo.user.repository.UserRepository;
import com.proyecto.demo.user.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
