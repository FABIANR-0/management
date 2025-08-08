package com.project.management.role.service.impl;

import com.project.management.auth.exception.AuthenticationFailedException;
import com.project.management.auth.service.AuthService;
import com.project.management.common.exception_handler.ConflictException;
import com.project.management.common.exception_handler.ResourceNotFoundException;
import com.project.management.permission.dto.PermissionDto;
import com.project.management.permission.entity.Permission;
import com.project.management.permission.repository.PermissionRepository;
import com.project.management.role.dto.CreateRoleRequest;
import com.project.management.role.dto.RoleDto;
import com.project.management.role.dto.RoleResponseDto;
import com.project.management.role.entity.Role;
import com.project.management.role.repository.RoleRepository;
import com.project.management.role.service.RoleService;
import com.project.management.user.dto.UserResponse;
import com.project.management.user.entity.User;
import com.project.management.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * El servicio RoleService proporciona métodos para gestionar los roles.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PermissionRepository permissionRepository;

    /**
     * Constructor del servicio
     * @param roleRepository Repositorio del modúlo role
     * @param userRepository Repositorio del modúlo user
     * @param authService    Servicio del modúlo auth
     * @param permissionRepository Repositorio del modúlo permissions
     */
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository, AuthService authService, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.authService = authService;
        this.permissionRepository = permissionRepository;
    }

    /**
     * Obtener datos de un rol en específico
     *
     * @param roleId Identíficador del rol a consultar
     * @return Objeto con la información del rol consultado con sus permisos
     */
    @Override
    @Transactional
    public RoleDto findById(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        List<Permission> permissions = role.getPermissions();
        List<PermissionDto> permissionsResponse = permissions.stream().map(permission -> PermissionDto.create(
                permission.getPermissionId(),
                permission.getName(),
                permission.getTitle(),
                permission.getChecked())).toList();
        long userCount = userRepository.countUsersByRole(role);
        return RoleDto.create(role.getRoleId(), role.getName(), role.getIsActive(), userCount, permissionsResponse);
    }

    @Override
    public Boolean existsByName(String name) {
        return roleRepository.existsByNameIgnoreCase(name);
    }

    /**
     * Obtener los roles del comercio que está autenticado
     *
     * @return Lista de roles del comercio con sus permisos
     */
    @Transactional
    @Override
    public List<RoleDto> getRolesByCommerce() {
        List<Role> roles = roleRepository.getAllByNameNot("Administrator");
        List<RoleDto> rolesResponse = new ArrayList<>();
        roles.forEach(role -> {
            if(!role.getName().equals("Administrator") && Boolean.TRUE.equals(role.getIsActive())) {
                Long userCount = userRepository.countUsersByRole(role);
                List<Permission> permissions = role.getPermissions();
                List<PermissionDto> permissionsResponse = permissions.stream().map(
                        permission -> PermissionDto.create(
                                permission.getPermissionId(),
                                permission.getName(),
                                permission.getTitle(),
                                permission.getChecked())
                ).toList();

                rolesResponse.add(RoleDto.create(role.getRoleId(), role.getName(), role.getIsActive(), userCount, permissionsResponse));
            }

        });
        return rolesResponse;
    }

    /**
     * Asignar roles a un usuario
     *
     * @param idsRole identíficadores de los roles que serán asignados al usuario
     * @param userId identíficador del usuario al que se le asignaran los roles
     */
    @Override
    @Transactional
    public void assignRole(List<UUID> idsRole, UUID userId) {
        //Obtener el usuario y el rol correspondientes según los ids proporcionados
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        List<Role> roles = roleRepository.getRolesByRoleIdIn(idsRole);

        if(roles.isEmpty()){
            throw new IllegalArgumentException("Los roles ingresados no existen");
        }
        // Asignar el rol al usuario
        user.addRole(roles);
        userRepository.save(user);
    }

    /**
     * Quitar rol a un usuario
     *
     * @param roleId identíficador del rol que será removido al usuario
     * @param userId identíficador del usuario al que se le quitara el rol
     */
    @Override
    @Transactional
    public void removeRole(UUID roleId, UUID userId) {
        //Obtener el usuario y el rol correspondientes según los ID proporcionados
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        // Remover el rol del usuario
        user.removeRole(role);
        userRepository.save(user);
    }

    /**
     * Eliminar rol
     *
     * @param roleId Identíficador del rol a eliminar
     */
    @Override
    public void deleteRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        if (role.getName().equals("Técnico") || role.getName().equals("Técnico QA"))
            throw new ConflictException(String.format("El rol de %s no puede ser eliminado", role.getName()));
        // Validar si el rol no puede ser eliminado debido a que tiene usuarios asignados
        if (!userRepository.findAllByRolesIs(List.of(role)).isEmpty()) {
            throw new ConflictException("El rol no ha sido eliminado debido a que se encuentra asociado a usuarios");
        }
        roleRepository.delete(role);
    }

    @Override
    public List<RoleResponseDto> getAllRolesByCommerce() {
        return roleRepository.getAllRolesByCommerce();
    }

    @Override
    public List<Role> getRolesByIds(List<UUID> rolesIds) {
        List<Role> roles = roleRepository.getRolesByRoleIdIn(rolesIds);
        if (roles.isEmpty()){
            throw new IllegalArgumentException("Los roles ingresados no existen");
        }
        return roles;
    }

    @Override
    public List<Role> getRolesOfMicrosoft(String[] roleName) {
        List<Role> roles = roleRepository.getRolesByNameIgnoreCaseIn(Arrays.stream(roleName).toList());
        if (roles.isEmpty()){
            throw new IllegalArgumentException("Los roles ingresados no existen");
        }
        return roles;
    }

    /**
     * Obtener usuarios de un rol
     *
     * @param roleId Identíficador del rol por el cual se consultaran los usuarios
     * @return  Lista de usuarios que tine dicho rol.
     */
    @Override
    public List<UserResponse> getUsersByRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"));
        List<User> users = userRepository.findAllByRolesIs(List.of(role));
        return users.stream().map(user -> UserResponse.response(
                user.getProfileImage(), user.getName(),
                user.getPerson().getName(),
                user.getPerson().getLastname(),
                user.getPerson().getPhone(),
                user.getPerson().getEmail(),
                user.getPerson().getCharge()
        )).toList();
    }

    /**
     * Crea un nuevo rol
     *
     * @param request el objeto de solicitud para crear el rol.
     * @return Objeto con los datos del rol creado
     */
    @Override
    public RoleDto create(CreateRoleRequest request) {
        User user = this.userAuth();
        Role role = Role.create(request.getNameRole());
        Role roleSave = roleRepository.save(role);
        long userCount = userRepository.countUsersByRole(role);
        return RoleDto.create(roleSave.getRoleId(), roleSave.getName(), roleSave.getIsActive(), userCount);
    }

    /**
     * Actualiza permisos del rol
     *
     * @param request Lista de permisos que se asignaran al rol
     * @param roleId identíficador del rol al que se le actualizaran los permisos
     */
    @Transactional
    @Override
    public void updatePermissions(List<PermissionDto> request, UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("rol no encontrado"));
        List<UUID> permissionsId = request.stream().map(PermissionDto::getPermissionId).toList();
        List<Permission> permissions = permissionRepository.getPermissionByPermissionIdIn(permissionsId);
        role.addPermissions(this.getFinalPermissions(permissions));
        roleRepository.save(role);
    }

    private List<Permission> getFinalPermissions(List<Permission> permissions){
        Set<Permission> processedPermissions = new HashSet<>(permissions);

        for (Permission permission : permissions) {
            UUID parentId = permission.getModule().getParentId();
            if (parentId != null) {
                // Busca el permiso asociado con el módulo que tiene el parentId
               processedPermissions.add(permissionRepository.getPermissionByModuleId(parentId));
            }
        }
        return new ArrayList<>(processedPermissions);
    }

    /**
     * Obtener datos del usuario autenticado
     * @return objeto con la información del usuario en sesión.
     */
    private User userAuth(){
        User user = authService.getUserAuthenticated();
        if(user == null)
            throw  new AuthenticationFailedException("No hay una sesión activa");
        return user;
    }
}

