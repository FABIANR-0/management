package com.project.management.module.entity;

import com.project.management.common.util.AuditEntity;
import com.project.management.permission.entity.Permission;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;
import java.util.UUID;

@Getter
@Entity
@Table(name = "module",schema = "main")
@EntityListeners(AuditingEntityListener.class)
public class Module extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "module_id")
    private UUID moduleId;

    @Column(name = "module_name",unique = true,nullable = false,length = 20)
    private String moduleName;

    @Column(name = "ico",unique = true,nullable = false)
    private String ico;

    @Column(name = "description",nullable = false,length = 200)
    private String description;

    @Column(name = "route",nullable = false)
    private String route;

    @Column(name = "orden",nullable = false,unique = true)
    private int order;

    @OneToMany(mappedBy = "module")
    private Set<Permission> permissions;
}
