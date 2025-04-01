package com.khi.bs.order.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class OrderItemCustomRepositoryImpl(
    private val queryFactory : JPAQueryFactory
) : OrderItemCustomRepository {

}