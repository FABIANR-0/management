package com.project.management.user.repository;

import com.project.management.permission.dto.PermissionResponse;
import com.project.management.user.dto.BasicUserInformationResponse;
import com.project.management.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryCustom {
    List<PermissionResponse> getPermissionByUserName(String userName);

    BasicUserInformationResponse getInfoBasicUser(UUID userId);

    User getUserNameAndEmail(String userName,String email);
}
