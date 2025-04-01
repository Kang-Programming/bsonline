package com.khi.bs.product.repository

import com.querydsl.jpa.impl.JPAQueryFactory

class ProductStockCustomRepositoryImpl(
    private val queryFactory: JPAQueryFactory
) : ProductStockCustomRepository {

}