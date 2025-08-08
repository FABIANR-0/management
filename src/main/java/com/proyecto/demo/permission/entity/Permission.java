package com.proyecto.demo.permission.entity;

import com.proyecto.demo.common.util.AuditEntity;
import com.proyecto.demo.module.entity.Module;
import com.proyecto.demo.role.entity.Role;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "permission",schema = "main")
@EntityListeners(AuditingEntityListener.class)
public class Permission extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "permission_id")
    private UUID permissionId;

    @Column(name = "title",nullable = false,length = 100)
    private String title;

    @Column(name = "permission_name",nullable = false,unique = true)
    private String permissionName;

    @Transient
    private boolean checked;

    @ManyToMany(mappedBy = "permissions",cascade = CascadeType.REFRESH)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Role> roles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id",nullable = false)
    private Module module;
}
