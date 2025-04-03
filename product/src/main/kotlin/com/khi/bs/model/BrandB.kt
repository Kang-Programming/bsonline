package com.khi.bs.model

import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "brand_b")
data class BrandB(
    @Id
    @Column(name = "brand_id")
    val brandId: String = UUID.randomUUID().toString(),

    @Column(name = "brand_name", nullable = false, unique = true)
    val brandName: String,

    @Column(name = "shipping_cost")
    val shippingCost: String? = null,

    @Column(length = 4000)
    val description: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null
)