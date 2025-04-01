package com.khi.bs.user.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class UserCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : UserCustomRepository {

}