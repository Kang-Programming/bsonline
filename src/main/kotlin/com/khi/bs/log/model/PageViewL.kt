package com.khi.bs.log.model

import com.khi.bs.user.model.UserB
import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "page_view_l")
data class PageViewL(
    @Id
    @Column(name = "log_id")
    val logId: String = UUID.randomUUID().toString(),

    @Column(name = "access_timestamp")
    val accessTimestamp: String? = null,

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column
    val page: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    val userB: UserB? = null // UserB와의 연관 관계 (선택 사항)
)