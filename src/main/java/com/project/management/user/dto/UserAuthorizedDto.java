package com.project.management.user.dto;

import com.project.management.common.validation.annotation.UserName;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UserAuthorizedDto {

    /**
     * Nombre del usuario.
     */
    @NotBlank
    @JsonProperty(value = "user_name")
    @Size(min = 3, max = 38)
    @UserName
    private String name;


    /**
     * Roles que serán asignados al usuario
     */
    @JsonProperty(value = "roles")
    private List<UUID> roles;

    @JsonProperty(value = "user_status")
    private String status;

    /**
     * Datos de la persona asociada al usuario.
     */
    @Valid
    @JsonProperty(value = "person")
    private PersonCreateDto person;

    /**
     * Imagen de perfil del usuario.
     */
    @JsonProperty(value = "profile_image")
    private String profileImage;

    public UserAuthorizedDto(String name, List<UUID> roles, String status, PersonCreateDto person, String profileImage) {
        this.name = name;
        this.roles = roles;
        this.status = status;
        this.person = person;
        this.profileImage = profileImage;
    }

    public  static UserAuthorizedDto create(String name, List<UUID> roles, String status, PersonCreateDto person, String profileImage) {
        return new UserAuthorizedDto(
                name,
                roles,
                status,
                person,
                profileImage
        );
    }
}
