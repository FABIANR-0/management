package com.proyecto.demo.documentType.entity;

import com.proyecto.demo.common.util.AuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "document_type",schema = "main")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class DocumentType extends AuditEntity {

    @Id
    @GeneratedValue
    @Column(name = "document_type_id")
    private UUID documentTypeId;

    @Column(name = "name",nullable = false,unique = true,length = 30)
    private String name;

    @Column(name = "code",unique = true,nullable = false,length = 3)
    private String code;
}
