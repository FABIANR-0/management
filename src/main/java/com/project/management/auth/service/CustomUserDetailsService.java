package com.project.management.auth.service;

import com.project.management.auth.dto.Permission;
import com.project.management.auth.security.service.UserDetailsImpl;
import com.project.management.user.entity.User;
import com.project.management.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.getUserByName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        List<Permission> permissions = userRepository.findPermissionsByUserName(userName);
        return UserDetailsImpl.build(user, permissions);
    }
}
