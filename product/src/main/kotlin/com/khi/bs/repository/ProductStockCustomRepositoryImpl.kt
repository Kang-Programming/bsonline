package com.khi.bs.repository

import com.querydsl.jpa.impl.JPAQueryFactory

open class ProductStockCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductStockCustomRepository {

}