package com.khi.bs.order.model

import com.khi.bs.user.model.UserB
import jakarta.persistence.*
import java.util.UUID
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "order_b")
data class OrderB(
    @Id
    @Column(name = "order_id")
    val orderId: String = UUID.randomUUID().toString(),

    @Column(name = "user_id", nullable = false)
    val userId: String,

    @Column(name = "delivery_address", nullable = false)
    val deliveryAddress: String,

    @Column(name = "order_status", nullable = false)
    val orderStatus: String,

    @Column(name = "order_amount", nullable = false)
    val orderAmount: String,

    @Column(name = "discount_amount", nullable = false)
    val discountAmount: String,

    @Column(name = "shipping_cost", nullable = false)
    val shippingCost: String,

    @Column(name = "order_date")
    val orderDate: String? = null,

    @Column(name = "payment_method")
    val paymentMethod: String? = null,

    @Column(name = "transaction_id", unique = true)
    val transactionId: String? = null,

    @Column(name = "created_at")
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),

    @Column(name = "updated_at")
    var updatedAt: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    val userB: UserB? = null // UserB와의 연관 관계
)