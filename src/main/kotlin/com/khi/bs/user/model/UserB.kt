package com.khi.bs.user.model

import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "user_b")
data class UserB(
    @Id
    @Column(name = "user_id")
    val userId: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(name = "phone_number")
    val phoneNumber: String? = null,

    @Column
    val address: String? = null,

    @Column(name = "registration_date")
    val registrationDate: String? = null,

    @Column(name = "last_login_date")
    val lastLoginDate: String? = null,

    @Column(name = "encrypted_field_1")
    val encryptedField1: String? = null,

    @Column(name = "encrypted_field_2")
    val encryptedField2: String? = null,

    @Column(name = "encrypted_field_3")
    val encryptedField3: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null
)