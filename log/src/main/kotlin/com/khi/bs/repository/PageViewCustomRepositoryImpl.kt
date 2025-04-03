package com.khi.bs.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
open class PageViewCustomRepositoryImpl(
    private val queryFactory : JPAQueryFactory,
) : PageViewCustomRepository {

}