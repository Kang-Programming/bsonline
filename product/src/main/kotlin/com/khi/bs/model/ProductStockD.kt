package com.khi.bs.model

import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(
    name = "product_stock_d",
    uniqueConstraints = [UniqueConstraint(columnNames = ["product_id", "product_option_id", "product_size"])]
)
data class ProductStockD(
    @Id
    @Column(name = "product_stock_id")
    val productStockId: String = UUID.randomUUID().toString(),

    @Column(name = "product_id", nullable = false)
    val productId: String,

    @Column(name = "product_option_id")
    val productOptionId: String? = null,

    @Column(name = "product_size", nullable = false)
    val productSize: String,

    @Column
    val qty: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null,

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    val productB: ProductB? = null, // ProductB와의 연관 관계

    @ManyToOne
    @JoinColumn(name = "product_option_id", referencedColumnName = "product_option_id", insertable = false, updatable = false)
    val productOptionD: ProductOptionD? = null // ProductOptionD와의 연관 관계
)