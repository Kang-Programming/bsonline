package com.khi.bs.product.repository

import com.khi.bs.product.model.ProductOptionD
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ProductOptionRepository : JpaRepository<ProductOptionD, String>, QuerydslPredicateExecutor<ProductOptionD>, ProductOptionCustomRepository {

}