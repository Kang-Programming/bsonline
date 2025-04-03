package com.khi.bs.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
open class UserCustomRepositoryImpl (
    private val queryFactory: JPAQueryFactory
) : UserCustomRepository {

}