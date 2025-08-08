package com.project.management.list.entity;

import com.project.management.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Getter
@Table(name = "list", schema = "main")
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Lists extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "list_id")
    private UUID listId;

    @Column(name = "name")
    private String name;


}
