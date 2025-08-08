package com.proyecto.demo.module.repository.impl;

import com.proyecto.demo.module.dto.UserModulesResponse;
import com.proyecto.demo.module.entity.Module;
import com.proyecto.demo.module.entity.Module_;
import com.proyecto.demo.module.repository.ModuleCustomRepository;
import com.proyecto.demo.permission.entity.Permission;
import com.proyecto.demo.permission.entity.Permission_;
import com.proyecto.demo.role.entity.Role;
import com.proyecto.demo.role.entity.Role_;
import com.proyecto.demo.user.entity.User;
import com.proyecto.demo.user.entity.User_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class ModuleCustomRepositoryImpl implements ModuleCustomRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<UserModulesResponse> getModuleOfUser(UUID userId) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        List<UserModulesResponse> result = new ArrayList<>();

        try {
            CriteriaQuery<UserModulesResponse> cq = cb.createQuery(UserModulesResponse.class);

            Root<User> root = cq.from(User.class);
            Join<User, Role> userRoleJoin = root.join(User_.roles, JoinType.INNER);
            Join<Role, Permission> rolePermissionJoin = userRoleJoin.join(Role_.permissions,JoinType.INNER);
            Join<Permission, Module> permissionModuleJoin = rolePermissionJoin.join(Permission_.module,JoinType.INNER);

            cq.select(cb.construct(
               UserModulesResponse.class,
                    permissionModuleJoin.get(Module_.moduleName),
                    permissionModuleJoin.get(Module_.ico),
                    permissionModuleJoin.get(Module_.description),
                    permissionModuleJoin.get(Module_.route),
                    permissionModuleJoin.get(Module_.order)
            ));
            cq.distinct(true);

            cq.where(cb.equal(root.get("userId"),userId));

            TypedQuery<UserModulesResponse> query = manager.createQuery(cq);
            result = query.getResultList();

        }catch (Exception ex){
            log.error("error en la consulta criteria getModuleOfUser [{}]",ex.getMessage());
        }
        manager.close();
        return result;
    }
}
