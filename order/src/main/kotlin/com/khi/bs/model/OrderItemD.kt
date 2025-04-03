package com.khi.bs.model

import com.khi.bs.model.ProductB
import com.khi.bs.model.ProductStockD
import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(
    name = "order_item_d",
    uniqueConstraints = [UniqueConstraint(columnNames = ["order_id", "product_stock_id"])]
)
data class OrderItemD(
    @Id
    @Column(name = "order_item_id")
    val orderItemId: String = UUID.randomUUID().toString(),

    @Column(name = "order_id", nullable = false)
    var orderId: String,

    @Column(name = "product_id", nullable = false)
    val productId: String,

    @Column(name = "product_stock_id", nullable = false)
    val productStockId: String,

    @Column
    val qty: String? = null,

    @Column(name = "product_price")
    val productPrice: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null,

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
    val orderB: OrderB? = null, // OrderB와의 연관 관계

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    val productB: ProductB? = null, // ProductB와의 연관 관계 (이름 충돌 방지를 위해 필드명 변경 고려)

    @ManyToOne
    @JoinColumn(name = "product_stock_id", referencedColumnName = "product_stock_id", insertable = false, updatable = false)
    val productStockD: ProductStockD? = null // ProductStockD와의 연관 관계
)