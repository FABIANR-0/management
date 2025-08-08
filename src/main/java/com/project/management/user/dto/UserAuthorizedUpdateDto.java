package com.project.management.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class UserAuthorizedUpdateDto {
    /**
     * Nombre del usuario.
     */
    @NotNull
    @NotEmpty
    @JsonProperty(value = "user_name")
    @Size(min = 3, max = 38)
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
    private PersonUpdateDto person;

    public UserAuthorizedUpdateDto(String name, List<UUID> roles, String status, PersonUpdateDto person) {
        this.name = name;
        this.roles = roles;
        this.status = status;
        this.person = person;
    }

    public  static  UserAuthorizedUpdateDto create(String name, List<UUID> roles, String status, PersonUpdateDto person){
        return new UserAuthorizedUpdateDto(
                name,
                roles,
                status,
                person
        );
    }
}
