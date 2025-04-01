package com.khi.bs.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ProductCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductCustomRepository {

}