package com.usermanagement.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @Column(name="cr_by",updatable = false)
    @CreatedBy
    private String crBy;

    @Column(name="cr_dtimes",updatable = false)
    @CreatedDate
    private LocalDateTime crDtimes;

    @Column(name="upd_by")
    @LastModifiedBy
    private String updBy;

    @Column(name="upd_dtimes")
    @LastModifiedDate
    private LocalDateTime updDtimes;
}
