package com.khi.bs.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class ProductOptionCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductOptionCustomRepository {

}