package org.spartaa3.movietogather.infra.audit

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {

    @Column(name = "createdAt")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @Column(name = "updatedAt")
    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
}