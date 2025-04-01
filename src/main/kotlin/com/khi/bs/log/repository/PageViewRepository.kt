package com.khi.bs.log.repository

import com.khi.bs.log.model.PageViewL
import com.khi.bs.product.repository.BrandCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface PageViewRepository : JpaRepository<PageViewL, String>, QuerydslPredicateExecutor<PageViewL>, BrandCustomRepository {
}