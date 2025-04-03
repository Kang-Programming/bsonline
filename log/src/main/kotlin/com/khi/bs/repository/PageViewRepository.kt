package com.khi.bs.repository

import com.khi.bs.model.PageViewL
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface PageViewRepository : JpaRepository<PageViewL, String>, QuerydslPredicateExecutor<PageViewL>, PageViewCustomRepository {
}