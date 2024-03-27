package org.spartaa3.movietogather.infra.audit

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseUserEntity : BaseTimeEntity() {
    @CreatedBy
    @Column(updatable = false)
    var createdBy: String = "system"

    @LastModifiedBy
    var updatedBy: String = "system"
}