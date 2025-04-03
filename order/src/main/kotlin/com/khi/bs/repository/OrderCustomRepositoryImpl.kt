package com.khi.bs.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
open class OrderCustomRepositoryImpl(
    private val queryFactory : JPAQueryFactory,
) : OrderCustomRepository {

}