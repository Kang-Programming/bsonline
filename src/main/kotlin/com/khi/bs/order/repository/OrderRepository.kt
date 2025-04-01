package com.khi.bs.order.repository

import com.khi.bs.order.model.OrderB
import com.khi.bs.product.repository.BrandCustomRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface OrderRepository : JpaRepository<OrderB, String>, QuerydslPredicateExecutor<OrderB>, BrandCustomRepository {

}