package com.project.management.user.service.impl;

import com.project.management.auth.exception.AuthenticationFailedException;
import com.project.management.auth.service.AuthService;
import com.project.management.common.exception_handler.ResourceNotFoundException;
import com.project.management.user.dto.PersonCreateDto;
import com.project.management.user.dto.PersonCreateRequest;
import com.project.management.user.dto.PersonUpdateDto;
import com.project.management.user.dto.PersonUpdateRequest;
import com.project.management.user.entity.Person;
import com.project.management.user.entity.User;
import com.project.management.user.repository.PersonRepository;
import com.project.management.user.service.PersonService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Clase que proporciona servicios relacionados con la gestión de personas.
 */
@Service
public class PersonServiceImpl implements PersonService {

    private final AuthService authService;

    private final PersonRepository personRepository;

    public PersonServiceImpl(AuthService authService, PersonRepository personRepository) {
        this.authService = authService;
        this.personRepository = personRepository;
    }

    @Override
    public Person create(User userDB, PersonCreateRequest request) {
        this.existEmail(request.getEmail());
        Person person = Person.create(
                request.getName(),
                request.getLastname(),
                request.getPhone(),
                this.isValidEmail(request.getEmail().toLowerCase(Locale.ROOT)),
                request.getCharge()
        );
        person.addUser(userDB);
        return personRepository.save(person);
    }

    @Override
    public void createPersonAuthorized(User user, PersonCreateDto request) {
        User userAuth = authService.getUserAuthenticated();
        if (userAuth == null) {
            throw new AuthenticationFailedException("No hay una sesión activa del comercio");
        }
        Person person = Person.create(
                request.getName(),
                request.getLastname(),
                request.getPhone(),
                this.isValidEmail(request.getEmail().toLowerCase(Locale.ROOT)),
                request.getCharge()
        );
        person.addUser(user);
        personRepository.save(person);
    }

    @Transactional
    public void updatePerson(PersonUpdateRequest request, User user) {
        Person person = personRepository.findById(user.getPerson().getPersonId()).orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada"));
        person.update(
                request.getName(),
                request.getLastname(),
                request.getPhone(),
                this.isValidEmail(request.getEmail().toLowerCase(Locale.ROOT)),
                request.getCharge()
        );
        personRepository.save(person);
    }

    @Override
    public Boolean isEmailExist(String email) {
        return personRepository.existsByEmail(email.toLowerCase(Locale.ROOT));
    }

    private String isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Correo mal estructurado");
        }
        return email;
    }

    @Override
    public void UpdatePersonAuthorized(PersonUpdateDto request, User user) {
        Person person = personRepository.findByUser(user);
        person.update(request.getName(),
                request.getLastname(),
                request.getPhone(),
                this.isValidEmail(request.getEmail().toLowerCase(Locale.ROOT)),
                request.getCharge()
        );
        personRepository.save(person);
    }

    private void existEmail(String email) {
        if (personRepository.existsByEmail(email.toLowerCase(Locale.ROOT))) {
            throw new IllegalArgumentException("El correo: " + email + " ya está registrado.");
        }
    }
}
