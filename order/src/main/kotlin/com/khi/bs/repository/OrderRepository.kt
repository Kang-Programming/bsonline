package com.khi.bs.repository

import com.khi.bs.model.OrderB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface OrderRepository : JpaRepository<OrderB, String>, QuerydslPredicateExecutor<OrderB>, OrderCustomRepository {

}