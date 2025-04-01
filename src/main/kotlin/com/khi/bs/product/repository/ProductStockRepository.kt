package com.khi.bs.product.repository

import com.khi.bs.product.model.ProductStockD
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ProductStockRepository : JpaRepository<ProductStockD, String>, QuerydslPredicateExecutor<ProductStockD>, ProductStockCustomRepository {

}