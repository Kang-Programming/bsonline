package com.khi.bs.order.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class OrderCustomRepositoryImpl(
    private val queryFactory : JPAQueryFactory,
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) : OrderCustomRepository {

}