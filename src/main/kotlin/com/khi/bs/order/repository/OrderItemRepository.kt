package com.khi.bs.order.repository

import com.khi.bs.order.model.OrderItemD
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface OrderItemRepository : JpaRepository<OrderItemD, String>, QuerydslPredicateExecutor<OrderItemD>,
    OrderItemCustomRepository {

}