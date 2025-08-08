package com.project.management.user.service;

import com.project.management.user.dto.PersonCreateDto;
import com.project.management.user.dto.PersonCreateRequest;
import com.project.management.user.dto.PersonUpdateDto;
import com.project.management.user.dto.PersonUpdateRequest;
import com.project.management.user.entity.Person;
import com.project.management.user.entity.User;


public interface PersonService {

    /**
     * Crea un nuevo usuario y su información personal asociada.
     *
     * @param user    El usuario que se creará.
     * @param request Los datos de creación de la información personal del usuario.
     */
    Person create(User user, PersonCreateRequest request);

    void createPersonAuthorized(User user, PersonCreateDto request);

    /**
     * Actualiza la información personal de un usuario.
     *
     * @param request Los datos de actualización de la información personal.
     * @param user    El usuario al que se le actualizará la información personal.
     */
    void updatePerson(PersonUpdateRequest request, User user);


    /**
     * Verifica si ya existe un usuario con la dirección de correo electrónico especificada.
     *
     * @param email La dirección de correo electrónico a verificar.
     * @return `true` si ya existe un usuario con esa dirección de correo electrónico, `false` en caso contrario.
     */
    Boolean isEmailExist(String email);

    void UpdatePersonAuthorized(PersonUpdateDto request, User user);
}
