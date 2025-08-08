package com.project.management.tag.entity;

import com.project.management.common.util.AuditEntity;
import com.project.management.task.entity.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "tag", schema = "main")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Tag extends AuditEntity {

    @Id
    @Column(name = "tag_id")
    @GeneratedValue
    private UUID tagId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color")
    private String color;

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;

    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public static Tag create(String name, String color) {
        return new Tag(name, color);
    }
}
