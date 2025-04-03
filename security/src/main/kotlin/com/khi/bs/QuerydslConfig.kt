package com.khi.bs

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class QuerydslConfig(
    private val entityManager: EntityManager
) {
    @Bean
    open fun jpaQueryFactory(): JPAQueryFactory {
        return JPAQueryFactory { entityManager }
    }
}