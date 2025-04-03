package com.khi.bs.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
open class ProductCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductCustomRepository {

}