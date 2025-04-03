package com.khi.bs.repository

import com.khi.bs.model.UserB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface UserRepository : JpaRepository<UserB, String>, QuerydslPredicateExecutor<UserB>, UserCustomRepository {

}