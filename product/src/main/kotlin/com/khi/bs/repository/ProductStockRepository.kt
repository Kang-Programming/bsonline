package com.khi.bs.repository

import com.khi.bs.model.ProductStockD
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ProductStockRepository : JpaRepository<ProductStockD, String>, QuerydslPredicateExecutor<ProductStockD>, ProductStockCustomRepository {

}