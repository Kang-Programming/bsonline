package com.khi.bs.model

import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "product_b")
data class ProductB(
    @Id
    @Column(name = "product_id")
    val productId: String = UUID.randomUUID().toString(),

    @Column(name = "product_code", nullable = false, unique = true)
    val productCode: String,

    @Column(name = "product_name")
    val productName: String? = null,

    @Column(name = "brand_id", nullable = false)
    val brandId: String,

    @Column
    val category: String? = null,

    @Column
    val color: String? = null,

    @Column(name = "price", nullable = false)
    val price: String,

    @Column(length = 4000)
    val description: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null,

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "brand_id", insertable = false, updatable = false)
    val brandB: BrandB? = null // BrandB와의 연관 관계
)