package com.project.management.user.controller;

import com.project.management.common.object.SearchByCriteria;
import com.project.management.common.parse.ParseFilters;
import com.project.management.user.dto.*;
import com.project.management.user.service.PersonService;
import com.project.management.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Controlador para la entidad User.
 */
@RestController
@RequestMapping("secure/user")
public class UserController {

    private final UserService userService;
    private final PersonService personService;

    /**
     * Constructor de UserController.
     *
     * @param userService Servicio de User.
     */
    public UserController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }

    /**
     * Obtiene al usuario según el criterio de búsqueda.
     *
     * @param userId El identificador del usuario que se desea buscar.
     * @return ResponseEntity con la página del usuario encontrado.
     */
    @GetMapping(value = "/{user_id}")
    @Operation(description = "Get user by userId")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<UserResponse> getUserId(@Parameter(description = "UUID of an user", required = true) @PathVariable("user_id") UUID userId) {
        return new ResponseEntity<>(userService.getUserId(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/exist_by_name/{user_name}")
    @Operation(description = "Get user exists by name")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<Boolean> getExistsUserByName(@Parameter(description = "Name of an user", required = true) @PathVariable("user_name") String userName) {
        boolean userExists = userService.existsByName(userName);
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }

    @GetMapping(value = "/exist_by_email/{user_email}")
    @Operation(description = "Get user exists by email")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<Boolean> getExistsUserByEmail(@Parameter(description = "Email of an user", required = true) @PathVariable("user_email") String userEmail) {
        boolean userExists = personService.isEmailExist(userEmail);
        return new ResponseEntity<>(userExists, HttpStatus.OK);
    }

    /**
     * Actualiza los datos de un usuario.
     *
     * @param request El objeto UserCreateRequest que contiene los nuevos datos del usuario.
     * @param userId  El UUID del usuario a actualizar.
     * @return ResponseEntity con el estado de la operación.
     */
    @PutMapping(value = "/update_person/{id}")
    @Operation(description = "Update person of an user by UUID")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<HttpStatus> updateCustomer(@RequestBody @Valid CustomerUpdateRequest request, @Parameter(description = "UUID of a User", required = true) @PathVariable("id") UUID userId) {
        userService.updateCustomer(request, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Envía un correo con un enlace y token para restablecer la contraseña del usuario.
     *
     * @param request La solicitud que contiene la información necesaria para el restablecimiento de contraseña.
     * @return Un ResponseEntity con HttpStatus OK si la operación se realiza con éxito.
     */
    @PostMapping("/forgot_password")
    @Operation(description = "send a link with token to recover password")
    public ResponseEntity<HttpStatus> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        userService.forgotPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Verifica las credenciales enviadas y la validez del token para establecer las nuevas credenciales de contraseña.
     *
     * @param request La solicitud que contiene la nueva contraseña y el token de verificación.
     * @return Un ResponseEntity con HttpStatus OK si la operación se realiza con éxito.
     * @throws IllegalArgumentException Si las contraseñas proporcionadas no coinciden.
     */
    @PostMapping("/reset_password")
    @Operation(description = "send password reset email")
    public ResponseEntity<HttpStatus> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        if (!request.getPassword().equals(request.getPasswordConfirmed())) {
            throw new IllegalArgumentException("Las contraseñas no coinciden");
        }
        userService.verifyTokenResetPassword(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reset_password_by_admin/{id}")
    @Operation(description = "Reset password of user by administrator")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<HttpStatus> resetPasswordUser(@Parameter(description = "UUID of a user", required = true) @PathVariable("id") UUID id) {
        userService.updatePasswordByAdmin(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Cambia la contraseña para usuarios logueados de la plataforma
     *
     * @param request
     * @return success
     */
    @PostMapping("/change_password")
    @Operation(description = "Change password by authenticated users")
    @ApiResponse(responseCode = "200", description = "updated password")
    public ResponseEntity<HttpStatus> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        if (request.getPassword().equals(request.getPasswordConfirmed())) {
            userService.changePassword(request);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        throw new IllegalArgumentException("Las contraseñas no coinciden");
    }

    @PutMapping("/deactivate_account/{id}")
    @Operation(description = "Deactivate Account of Customer")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<HttpStatus> deactivateAccountCustomer(@Parameter(description = "UUID of a User", required = true) @PathVariable("id") UUID userId) {
        userService.deactivateAccountCustomer(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/update_double_factor")
    @Operation(description = "Edit an update double factor")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<HttpStatus> updateDoubleFactor(@RequestBody @Valid DoubleFactorAuthenticationRequest request) {
        userService.updateDoubleFactor(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/profile")
    @Operation(description = "Get an user with an UUID")
    @ApiResponse(responseCode = "200", description = "success")
    public ResponseEntity<UserResponseDto> getByUserAuthenticated() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    @GetMapping("/authenticator_update")
    @Operation(description = "update google authenticator")
    @ApiResponse(responseCode = "200", description = "200")
    public ResponseEntity<UserResponseDto> updateAuthenticator() {
        return new ResponseEntity<>(userService.updateAuthenticator(), HttpStatus.OK);
    }

    @PostMapping(value = "/authorized_by_admin")
    @PreAuthorize("hasPermission('HttpStatus', 'CREATE_USER')")
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, description = "Create user from admin")
    @ApiResponse(responseCode = "201", description = "created")
    public ResponseEntity<HttpStatus> createUserAuthorizedByAdmin(@Valid @RequestBody UserAuthorizedDto request) {
        userService.registerNewUserAuthorizedByAdmin(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all/authorized_by_admin")
    @Operation(description = "Get all users authorized by Admin")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<UserAuthorized> getUsersAuthorizedByAdmin(@RequestParam Map<String, Serializable> params) {
        SearchByCriteria search = new SearchByCriteria(
                ParseFilters.parseFilters(params),
                Optional.ofNullable((String) params.get("order_by")),
                Optional.ofNullable((String) params.get("order")),
                ParseFilters.serializableToOptionalInteger(params.get("limit")),
                ParseFilters.serializableToOptionalInteger(params.get("offset"))
        );
        String quickSearch = params.get("quick_search") != null ? params.get("quick_search").toString() : null;
        return new ResponseEntity<>(userService.getUsersAuthorizedByAdmin(search, quickSearch), HttpStatus.OK);
    }

    @PutMapping(value = "/update_user/admin/{id}")
    @Operation(description = "Update user authenticated by uuid")
    @ApiResponse(responseCode = "204", description = "No Content")
    public ResponseEntity<HttpStatus> updateUserAuthorizedById(@RequestBody @Valid UserAuthorizedUpdateDto request, @Parameter(description = "UUID of a User", required = true) @PathVariable("id") UUID userId) {
        userService.updateUserAuthorized(request, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/authorized/{user_id}")
    @Operation(description = "Get user authorized by userId")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<UserAuthorizedDto> getUserAuthenticatedById(@Parameter(description = "UUID of an user", required = true) @PathVariable("user_id") UUID userId) {
        return new ResponseEntity<>(userService.getUserAuthorizedByUserId(userId), HttpStatus.OK);
    }

    @GetMapping(value = "all/user_technical")
    @Operation(description = "Get all users technical")
    @ApiResponse(responseCode = "200", description = "Success")
    public ResponseEntity<List<UserTechnical>> getAllUsersByRole() {
        return new ResponseEntity<>(userService.getAllUsersByRole(), HttpStatus.OK);
    }
}
