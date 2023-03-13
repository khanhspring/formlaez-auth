package com.formlaez.auth.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class JpaBaseEntity extends JpaTimestampEntity {
    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

}
