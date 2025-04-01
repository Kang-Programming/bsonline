package com.khi.bs.product.repository

import com.khi.bs.product.model.BrandB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface BrandRepository : JpaRepository<BrandB, String>, QuerydslPredicateExecutor<BrandB>, BrandCustomRepository {
    fun existsByBrandName(brandName: String): Boolean
}