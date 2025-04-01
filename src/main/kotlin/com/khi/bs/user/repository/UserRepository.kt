package com.khi.bs.user.repository

import com.khi.bs.user.model.UserB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor

interface UserRepository : JpaRepository<UserB, String>, QuerydslPredicateExecutor<UserB>, UserCustomRepository {

}