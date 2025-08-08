package com.proyecto.demo.user.repository.impl;

import com.proyecto.demo.permission.dto.PermissionResponse;
import com.proyecto.demo.permission.entity.Permission;
import com.proyecto.demo.permission.entity.Permission_;
import com.proyecto.demo.role.entity.Role;
import com.proyecto.demo.role.entity.Role_;
import com.proyecto.demo.user.dto.BasicUserInformationResponse;
import com.proyecto.demo.user.entity.Person;
import com.proyecto.demo.user.entity.Person_;
import com.proyecto.demo.user.entity.User;
import com.proyecto.demo.user.entity.User_;
import com.proyecto.demo.user.repository.UserRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<PermissionResponse> getPermissionByUserName(String userName) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        List<PermissionResponse> result = new ArrayList<>();

        try {
            CriteriaQuery<PermissionResponse> cq = cb.createQuery(PermissionResponse.class);

            Root<User> root = cq.from(User.class);
            Join<User, Role> userRoleJoin = root.join(User_.roles, JoinType.INNER);
            Join<Role,Permission> permissionJoin = userRoleJoin.join(Role_.permissions,JoinType.INNER);

            cq.select(cb.construct(
                    PermissionResponse.class,
                    permissionJoin.get(Permission_.permissionName)
            ));

            cq.where(cb.equal(root.get(User_.userName),userName));

            cq.distinct(true);

            TypedQuery<PermissionResponse> query = manager.createQuery(cq);
            result = query.getResultList();

        }catch (Exception ex){
            log.error("error en la consulta criteria getPermissionByUserName {}",ex.getMessage());
        }
        manager.close();
        return result;
    }

    @Override
    public BasicUserInformationResponse getInfoBasicUser(UUID userId) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        BasicUserInformationResponse result = new BasicUserInformationResponse();

        try {
            CriteriaQuery<BasicUserInformationResponse> cq = cb.createQuery(BasicUserInformationResponse.class);

            Root<User> root = cq.from(User.class);
            Join<User, Person> userPersonJoin = root.join(User_.person,JoinType.INNER);

            cq.select(cb.construct(
                    BasicUserInformationResponse.class,
                    root.get(User_.administrator),
                    root.get(User_.profileImage),
                    userPersonJoin.get(Person_.name),
                    userPersonJoin.get(Person_.lastname)
            ));

            cq.where(cb.equal(root.get(User_.userId),userId));

            TypedQuery<BasicUserInformationResponse> query = manager.createQuery(cq);

            result = query.getSingleResult();

        }catch (Exception ex){
            log.error("error en la consulta criteria getInfoBasicUser [{}]",ex.getMessage());
        }
        manager.close();
        return result;
    }

    @Override
    public User getUserNameAndEmail(String userName, String email) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        User result = null;

        try {
            CriteriaQuery<User> cq = cb.createQuery(User.class);

            Root<User> root = cq.from(User.class);
            Join<User,Person> userPersonJoin = root.join(User_.person,JoinType.INNER);

            cq.select(root);

            cq.where(
                    cb.and(cb.equal(root.get(User_.userName),userName),cb.equal(userPersonJoin.get(Person_.email),email))
            );

            TypedQuery<User> query = manager.createQuery(cq);
            result = query.getSingleResult();
        }catch (Exception ex){
            log.error("error en la consulta criteria getUserNameAndEmail [{}]",ex.getMessage());
        }
        manager.close();
        return result;
    }
}
