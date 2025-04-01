package com.khi.bs.log.repository

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class PageViewCustomRepositoryImpl(
    private val queryFactory : JPAQueryFactory,
) : PageViewCustomRepository {

}