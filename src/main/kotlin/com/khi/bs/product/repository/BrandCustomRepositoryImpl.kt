package com.khi.bs.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class BrandCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : BrandCustomRepository {

}