package com.proyecto.demo.authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.proyecto.demo.module.dto.UserModulesResponse;
import com.proyecto.demo.permission.dto.PermissionResponse;
import com.proyecto.demo.user.dto.BasicUserInformationResponse;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponse {

    @JsonProperty(value = "user_id", required = true)
    private UUID userId;

    @JsonProperty(value = "token_jwt", required = true)
    private String jwt;

    @JsonProperty(value = "type_token",required = true)
    private String typeToken;

    @JsonProperty(value = "refresh_token",required = true)
    private UUID refreshToken;

    @JsonProperty(value = "user_data",required = true)
    private BasicUserInformationResponse basicUserInformationResponse;

    @JsonProperty(value = "user_module",required = true)
    private List<UserModulesResponse> userModulesResponses;

    @JsonProperty(value = "user_permission",required = true)
    private List<PermissionResponse> permissionResponses;

    public LoginResponse(UUID userId) {
        this.userId = userId;
    }

    public LoginResponse(String jwt, UUID refreshToken, BasicUserInformationResponse basicUserInformationResponse, List<UserModulesResponse> userModulesResponses, List<PermissionResponse> permissionResponses) {
        this.jwt = jwt;
        this.typeToken = "Bearer";
        this.refreshToken = refreshToken;
        this.basicUserInformationResponse = basicUserInformationResponse;
        this.userModulesResponses = userModulesResponses;
        this.permissionResponses = permissionResponses;
    }
}
