package com.project.management.user.repository.impl;

import com.project.management.auth.dto.AuthModule;
import com.project.management.auth.dto.Permission;
import com.project.management.common.criteria.Criteria;
import com.project.management.common.criteria.CriteriaPredicate;
import com.project.management.module.entity.Module;
import com.project.management.module.entity.Module_;
import com.project.management.permission.entity.Permission_;
import com.project.management.role.entity.Role;
import com.project.management.role.entity.Role_;
import com.project.management.user.dto.UserResponse;
import com.project.management.user.dto.UserTechnical;
import com.project.management.user.dto.UsersAuthorizedResponse;
import com.project.management.user.entity.Person;
import com.project.management.user.entity.Person_;
import com.project.management.user.entity.User;
import com.project.management.user.entity.User_;
import com.project.management.user.repository.UserRepositoryCustom;
import com.project.management.user.status.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * Implementación personalizada del repositorio de usuarios.
 */
@Slf4j
@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    @Override
    public UserResponse getUserId(UUID userId) {
        /* --- Obtener Criteria ---*/
        CriteriaBuilder cb = em.getCriteriaBuilder();
        UserResponse result = null;

        try {
            /* --- Crear consulta para la tabla de datos ---*/
            CriteriaQuery<UserResponse> cq = cb.createQuery(UserResponse.class);

            /* --- Definir cláusula FROM ---*/
            Root<User> root = cq.from(User.class);
            Join<User, Person> userPersonJoin = root.join("person", JoinType.INNER);

            /* --- Definir proyección DTO ---*/
            cq.select(cb.construct(
                    UserResponse.class,
                    root.get(User_.profileImage),
                    root.get(User_.name),
                    userPersonJoin.get(Person_.name),
                    userPersonJoin.get(Person_.lastname),
                    userPersonJoin.get(Person_.phone),
                    userPersonJoin.get(Person_.email),
                    userPersonJoin.get(Person_.charge)
            ));

            /* --- Definir predicado para filtros de datos ---*/
            Predicate whereClause = cb.and(
                    cb.equal(root.get(User_.userId), userId),
                    cb.equal(root.get(User_.status), UserStatus.ACTIVE)
            );

            cq.where(whereClause);

            /* --- Ejecutar consulta ---*/
            TypedQuery<UserResponse> q = em.createQuery(cq);

            /* --- Obtener lista ---*/
            result = q.getSingleResult();

        } catch (Exception ex) {
            log.error("Criteria getUserId {}", ex.getMessage());

        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public List<AuthModule> findModulesByUser(UUID userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<AuthModule> modules = new ArrayList<>();

        try {
            /* --- Create query ---*/
            CriteriaQuery<AuthModule> cq = cb.createQuery(AuthModule.class);
            /*--- Define FROM clause ---*/
            Root<User> root = cq.from(User.class);
            Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);
            Join<Role, com.project.management.permission.entity.Permission> rolePermissionJoin = userRoleJoin.join("permissions", JoinType.INNER);
            Join<com.project.management.permission.entity.Permission, Module> permissionModuleJoin = rolePermissionJoin.join("module", JoinType.INNER);

            /* --- Define DTO projection ---*/
            cq.select(cb.construct(
                    AuthModule.class,
                    permissionModuleJoin.get(Module_.moduleId),
                    permissionModuleJoin.get(Module_.parentId),
                    permissionModuleJoin.get(Module_.name),
                    permissionModuleJoin.get(Module_.description),
                    permissionModuleJoin.get(Module_.route),
                    permissionModuleJoin.get(Module_.order),
                    permissionModuleJoin.get(Module_.secondaryIcon),
                    permissionModuleJoin.get(Module_.primaryIcon),
                    permissionModuleJoin.get(Module_.tertiaryIcon)
            ));

            /* --- Define WHERE clause ---*/
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    cb.and(
                            cb.equal(root.get(User_.userId), userId),
                            cb.isNull(permissionModuleJoin.get(Module_.parentId))
                    )
            );

            cq.where(predicates.toArray(new Predicate[0]));

            /* Agrupar por atributos de la entidad Module*/
            cq.groupBy(
                    permissionModuleJoin.get(Module_.moduleId),
                    permissionModuleJoin.get(Module_.parentId),
                    permissionModuleJoin.get(Module_.name),
                    permissionModuleJoin.get(Module_.description),
                    permissionModuleJoin.get(Module_.route),
                    permissionModuleJoin.get(Module_.order),
                    permissionModuleJoin.get(Module_.secondaryIcon),
                    permissionModuleJoin.get(Module_.primaryIcon),
                    permissionModuleJoin.get(Module_.tertiaryIcon)
            );

            /* --- Execute query ---*/
            TypedQuery<AuthModule> q = em.createQuery(cq);
            List<AuthModule> authModules = q.getResultList();
            authModules.forEach(authModule -> authModule.addSubmodules(this.findSubModulesByParentId(authModule.getModuleId(), userId)));
            modules = authModules;

        } catch (Exception ex) {
            log.error("Error al obtener los módulos por usuario {}", ex.getMessage());
        }
        em.close();
        return modules;
    }

    public List<AuthModule> findSubModulesByParentId(UUID parentId, UUID userId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<AuthModule> submodules = new ArrayList<>();

        try {
            /* --- Create query ---*/
            CriteriaQuery<AuthModule> cq = cb.createQuery(AuthModule.class);
            /*--- Define FROM clause ---*/
            Root<User> root = cq.from(User.class);
            Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);
            Join<Role, com.project.management.permission.entity.Permission> rolePermissionJoin = userRoleJoin.join("permissions", JoinType.INNER);
            Join<com.project.management.permission.entity.Permission, Module> permissionModuleJoin = rolePermissionJoin.join("module", JoinType.INNER);

            /* --- Define DTO projection ---*/
            cq.select(cb.construct(
                    AuthModule.class,
                    permissionModuleJoin.get(Module_.moduleId),
                    permissionModuleJoin.get(Module_.parentId),
                    permissionModuleJoin.get(Module_.name),
                    permissionModuleJoin.get(Module_.description),
                    permissionModuleJoin.get(Module_.route),
                    permissionModuleJoin.get(Module_.order),
                    permissionModuleJoin.get(Module_.primaryIcon),
                    permissionModuleJoin.get(Module_.secondaryIcon),
                    permissionModuleJoin.get(Module_.tertiaryIcon)
            ));

            /* --- Define WHERE clause ---*/
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(
                    cb.and(
                            cb.equal(permissionModuleJoin.get(Module_.parentId), parentId),
                            cb.equal(root.get(User_.userId), userId)
                    )
            );

            cq.where(predicates.toArray(new Predicate[0]));

            /* Agrupar por atributos de la entidad Module*/
            cq.groupBy(
                    permissionModuleJoin.get(Module_.moduleId),
                    permissionModuleJoin.get(Module_.parentId),
                    permissionModuleJoin.get(Module_.name),
                    permissionModuleJoin.get(Module_.description),
                    permissionModuleJoin.get(Module_.route),
                    permissionModuleJoin.get(Module_.order),
                    permissionModuleJoin.get(Module_.primaryIcon),
                    permissionModuleJoin.get(Module_.secondaryIcon),
                    permissionModuleJoin.get(Module_.tertiaryIcon)
            );

            /* --- Execute query ---*/
            TypedQuery<AuthModule> q = em.createQuery(cq);
            submodules = q.getResultList();

        } catch (Exception ex) {
            log.error("Error al obtener los submódulos por moduleId {}", ex.getMessage());
        }
        em.close();
        return submodules;
    }

    @Override
    public List<Permission> findPermissionsByUserName(String userName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<Permission> permissions = new ArrayList<>();

        try {
            /* --- Create query ---*/
            CriteriaQuery<Permission> cq = cb.createQuery(Permission.class);

            /* --- Define FROM clause ---*/
            Root<User> root = cq.from(User.class);
            Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);
            Join<Role, com.project.management.permission.entity.Permission> rolePermissionJoin = userRoleJoin.join("permissions", JoinType.INNER);

            cq.select(cb.construct(
                    Permission.class,
                    rolePermissionJoin.get(Permission_.name)
            ));

            /* --- Define WHERE clause ---*/
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get(User_.name), userName));

            cq.where(predicates.toArray(new Predicate[0]));

            cq.groupBy(rolePermissionJoin.get(Permission_.name));

            /* --- Execute query ---*/
            TypedQuery<Permission> q = em.createQuery(cq);
            permissions = q.getResultList();

        } catch (Exception e) {
            log.error("Criteria permissions login [{}]", e.getMessage());
        }
        em.close();
        return permissions;
    }

    @Override
    public List<UsersAuthorizedResponse> getUsersAuthorizedByAdmin(Criteria criteria, String search) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        List<UsersAuthorizedResponse> result = null;

        try {
            CriteriaQuery<UsersAuthorizedResponse> cq = cb.createQuery(UsersAuthorizedResponse.class);

            Root<User> root = cq.from(User.class);
            Join<User,Person> userPersonJoin = root.join(User_.person,JoinType.INNER);

            cq.select(cb.construct(
                    UsersAuthorizedResponse.class,
                    root.get(User_.userId),
                    root.get(User_.name),
                    root.get(User_.status),
                    root.get(User_.profileImage),
                    root.get(User_.lastLogin),
                    userPersonJoin.get(Person_.charge)
            ));

            Predicate searchFilter = cb.and(
                    cb.notEqual(root.get(User_.administrator), true)
            );

            List<Predicate> predicates = new ArrayList<>();
            if (search != null){
                String searchFormat = "%" + search.toUpperCase(Locale.ROOT) + "%";
                UserStatus userStatus = switch (search.toLowerCase(Locale.ROOT)) {
                    case "activo" -> UserStatus.ACTIVE;
                    case "inactivo" -> UserStatus.INACTIVE;
                    case "bloqueado" -> UserStatus.LOCKED;
                    default -> null;
                };
                predicates.add(cb.like(cb.upper(root.get(User_.name)),searchFormat));
                predicates.add(cb.like(cb.upper(userPersonJoin.get(Person_.charge)),searchFormat));
                if (userStatus != null){
                    predicates.add(cb.equal(root.get(User_.status), userStatus));
                }
                searchFilter =  cb.and(searchFilter, cb.or(predicates.toArray(new Predicate[0])) );
            }

            CriteriaPredicate<User, UsersAuthorizedResponse> criteriaPredicate = new CriteriaPredicate<>(cb);

            TypedQuery<UsersAuthorizedResponse> query = em.createQuery(criteriaPredicate.convertSearchAndFilters(cq,criteria,root,searchFilter));

            result = query.getResultList();
        }catch (Exception ex){
            log.error("Error en consulta Criteria getUsersCommerce  {} ",ex.getMessage());
        }
        em.close();
        return result;
    }

    @Override
    public Long countUserAuthorized() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        Long totalRows = null;

        try {
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);

            Root<User> root = cq.from(User.class);
            Join<User,Person> userPersonJoin = root.join(User_.person,JoinType.INNER);

            cq.select(cb.count(root));
            cq.where(
                    cb.notEqual(root.get(User_.administrator), true)
            );

            TypedQuery<Long>  query = em.createQuery(cq);

            totalRows = query.getSingleResult();

        }catch (Exception ex){
            log.error("Error en consulta Criteria  countUserCommerce  {} ",ex.getMessage());
        }
        em.close();
        return totalRows;
    }

    @Override
    public List<UserTechnical> getAllUsersByRole() {
        List<UserTechnical> result = new ArrayList<>();

        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<UserTechnical> cq = cb.createQuery(UserTechnical.class);

            Root<User> root = cq.from(User.class);
            Join<User, Person> userPersonJoin = root.join("person", JoinType.INNER);
            Join<User, Role> userRoleJoin = root.join("roles", JoinType.INNER);

            cq.select(cb.construct(
                    UserTechnical.class,
                    root.get(User_.userId),
                    root.get(User_.name),
                    userPersonJoin.get(Person_.name),
                    userPersonJoin.get(Person_.lastname)
            ));

            cq.where(cb.equal(userRoleJoin.get(Role_.roleId), UUID.fromString("2e16c30c-a4d7-4d42-ac54-1848988e4fbb")));

            result = em.createQuery(cq).getResultList();

        } catch (Exception e) {
            log.error("Criteria getAllUsersByRole [{}]", e.getMessage());
        }
        return result;
    }
}
