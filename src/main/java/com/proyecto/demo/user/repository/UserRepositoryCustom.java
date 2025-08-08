package com.proyecto.demo.user.repository;

import com.proyecto.demo.permission.dto.PermissionResponse;
import com.proyecto.demo.user.dto.BasicUserInformationResponse;
import com.proyecto.demo.user.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepositoryCustom {
    List<PermissionResponse> getPermissionByUserName(String userName);

    BasicUserInformationResponse getInfoBasicUser(UUID userId);

    User getUserNameAndEmail(String userName,String email);
}
