package com.project.management.user.service;

import com.project.management.permission.dto.PermissionResponse;
import com.project.management.user.dto.BasicUserInformationResponse;
import com.project.management.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserServiceShared {
    User getUserByUserName(String userName);

    User getUserById(UUID userId);

    List<PermissionResponse> getAllPermission(String userName);

    void saveUser(User user);

    BasicUserInformationResponse getInfoBasicUser(UUID userId);

    User  getUserNameAndEmail(String userName,String email);

    User getUserOfResetPassword(UUID userId,String userName);
}
