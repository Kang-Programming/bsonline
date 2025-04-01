package com.khi.bs.product.model

import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(
    name = "product_option_d",
    uniqueConstraints = [UniqueConstraint(columnNames = ["product_id", "option_name", "option_value"])]
)
data class ProductOptionD(
    @Id
    @Column(name = "product_option_id")
    val productOptionId: String = UUID.randomUUID().toString(),

    @Column(name = "product_id", nullable = false)
    val productId: String,

    @Column(name = "option_name")
    val optionName: String? = null,

    @Column(name = "option_value")
    val optionValue: String? = null,

    @Column(name = "additional_price")
    val additionalPrice: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null,

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    val productB: ProductB? = null // ProductB와의 연관 관계
)