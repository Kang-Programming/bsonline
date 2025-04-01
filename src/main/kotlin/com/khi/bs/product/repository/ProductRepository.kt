package com.khi.bs.product.repository

import com.khi.bs.product.model.ProductB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface ProductRepository : JpaRepository<ProductB, String>, QuerydslPredicateExecutor<ProductB>, ProductCustomRepository {

}