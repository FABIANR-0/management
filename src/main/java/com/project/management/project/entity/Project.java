package com.project.management.project.entity;


import com.project.management.common.util.AuditEntity;
import com.project.management.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "project", schema = "main")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Project extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "project_id", unique = true, nullable = false)
    private UUID projectId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image_base64", columnDefinition = "TEXT")
    private String imageBase64;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Project() {
    }

    public Project(String name, String description, String imageBase64, User user) {
        this.name = name;
        this.description = description;
        this.imageBase64 = imageBase64;
        this.user = user;
    }

    public static Project create(String name, String description, String imageBase64, User user) {
        return new Project(name, description, imageBase64, user);
    }
}
