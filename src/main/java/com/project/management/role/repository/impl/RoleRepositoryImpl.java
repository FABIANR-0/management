package com.project.management.role.repository.impl;

import com.project.management.role.dto.RoleResponseDto;
import com.project.management.role.entity.Role;
import com.project.management.role.entity.Role_;
import com.project.management.role.repository.RoleRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class RoleRepositoryImpl implements RoleRepositoryCustom {

    @PersistenceContext
    EntityManager manager;

    @Override
// Método para obtener todos los roles de un comercio, excluyendo el rol de "Administrator" y solo aquellos que están activos.
    public List<RoleResponseDto> getAllRolesByCommerce() {
        // Obtener el CriteriaBuilder del EntityManager.
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        // Inicializar la lista de resultados como null.
        List<RoleResponseDto> result = null;
        try {
            // Crear una consulta Criteria para RoleResponseDto.
            CriteriaQuery<RoleResponseDto> cq = cb.createQuery(RoleResponseDto.class);
            // Definir la raíz de la consulta.
            Root<Role> root = cq.from(Role.class);
            // Seleccionar los campos que se desean en el resultado y construir el DTO.
            cq.select(
                    cb.construct(
                            RoleResponseDto.class,
                            root.get(Role_.roleId),  // Obtener el ID del rol.
                            root.get(Role_.name)     // Obtener el nombre del rol.
                    )
            );
            // Definir las condiciones de la consulta.
            cq.where(
                    cb.and(
                            cb.equal(root.get(Role_.isActive), true),    // El rol debe estar activo.
                            cb.notEqual(root.get(Role_.name), "Administrator")  ,
                            cb.notEqual(root.get(Role_.name),"Azure")// El nombre del rol no debe ser "Administrator".
                    )
            );
            // Crear una consulta tipada a partir del CriteriaQuery.
            TypedQuery<RoleResponseDto> query = manager.createQuery(cq);
            // Ejecutar la consulta y obtener la lista de resultados.
            result = query.getResultList();
        } catch (Exception ex) {
            // Registrar el error en el log si ocurre una excepción.
            log.error("Error en criteria getAllRolesByCommerce [{}]", ex.getMessage());
        }

        // Cerrar el EntityManager.
        manager.close();

        // Devolver la lista de resultados.
        return result;
    }
}
