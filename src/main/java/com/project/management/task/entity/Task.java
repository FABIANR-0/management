package com.project.management.task.entity;

import com.project.management.common.util.AuditEntity;
import com.project.management.list.entity.Lists;
import com.project.management.project.entity.Project;
import com.project.management.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "task", schema = "main")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Task extends AuditEntity {

    @Id
    @Column(name = "task_id")
    @GeneratedValue
    private UUID taskId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private Lists lists;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            schema = "main",
            name = "tag_task",
            joinColumns = @JoinColumn(name = "task_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "tag_id", nullable = false),
            uniqueConstraints = @UniqueConstraint(name = "uc_task_tag", columnNames = {"task_id", "tag_id"})
    )
    private List<Tag> tags;


}
