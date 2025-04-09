package com.khi.bs.service

import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class StatisticsServiceImpl(
    private val entityManager: EntityManager
) : StatisticsService {

    override fun getMonthlyOrderStatistics(): List<Map<String, Any>> {
        val sql = """
        SELECT
            TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM') AS month,
            COUNT(DISTINCT order_b.user_id) AS monthly_order_count,
            COUNT(order_b.order_id) AS total_orders,
            SUM(TO_NUMBER(order_b.order_amount)) AS total_order_amount,
            SUM(TO_NUMBER(order_b.discount_amount)) AS total_discount_amount,
            SUM(TO_NUMBER(order_b.shipping_cost)) AS total_shipping_cost
        FROM order_b
        GROUP BY TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM')
        ORDER BY month
    """.trimIndent()

        val query: Query = entityManager.createNativeQuery(sql)
        val rawResultList: List<*> = query.resultList

        return rawResultList.filterNotNull().mapNotNull {
            if (it is Array<*> && it.size == 6 &&
                it[0] is String && it[1] is Number && it[2] is Number &&
                it[3] is Number && it[4] is Number && it[5] is Number
            ) {
                mapOf(
                    "month" to it[0] as String,
                    "monthlyOrderCount" to (it[1] as Number).toLong(),
                    "totalOrders" to (it[2] as Number).toLong(),
                    "totalOrderAmount" to (it[3] as Number).toLong(),
                    "totalDiscountAmount" to (it[4] as Number).toLong(),
                    "totalShippingCost" to (it[5] as Number).toLong()
                )
            } else {
                // 예상치 못한 결과 구조에 대한 로깅 또는 처리
                println("Unexpected result structure: $it")
                null // 예상치 못한 결과는 필터링
            }
        }
    }

    override fun getMonthlyActiveUserCount(): List<Map<String, Any>> {
        val sql = """
        SELECT
            TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM') AS month,
            COUNT(DISTINCT order_b.user_id) AS monthly_active_user_count
        FROM order_b
        GROUP BY TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM')
        ORDER BY month
    """.trimIndent()

        val query: Query = entityManager.createNativeQuery(sql)
        val rawResultList: List<*> = query.resultList

        return rawResultList.filterNotNull().mapNotNull {
            if (it is Array<*> && it.size == 2 && it[0] is String && it[1] is Number) {
                mapOf(
                    "month" to it[0] as String,
                    "monthlyActiveUserCount" to (it[1] as Number).toLong()
                )
            } else {
                println("Unexpected result structure for getMonthlyActiveUserCount: $it")
                null
            }
        }
    }

    override fun getUsersWithAvgOrderAbove100K(): List<Map<String, Any>> {
        val sql = """
        SELECT
            TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM') AS month,
            COUNT(DISTINCT order_b.user_id) AS users_with_avg_order_amount_above_100k
        FROM
            order_b
        WHERE
            order_b.user_id IN (
                SELECT user_id
                FROM order_b
                WHERE TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM') BETWEEN '2025-01' AND '2025-03'
                GROUP BY user_id
                HAVING AVG(TO_NUMBER(order_b.order_amount)) > 100000
            )
        GROUP BY
            TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM')
        ORDER BY
            month
    """.trimIndent()

        val query: Query = entityManager.createNativeQuery(sql)
        val rawResultList: List<*> = query.resultList

        return rawResultList.filterNotNull().mapNotNull {
            if (it is Array<*> && it.size == 2 && it[0] is String && it[1] is Number) {
                mapOf(
                    "month" to it[0] as String,
                    "usersWithAvgOrderAmountAbove100K" to (it[1] as Number).toLong()
                )
            } else {
                println("Unexpected result structure for getUsersWithAvgOrderAbove100K: $it")
                null
            }
        }
    }

    override fun getFirstTimeBuyersOfBrandInMarch(): List<Map<String, Any>> {
        val sql = """
        SELECT
            COUNT(DISTINCT order_b.user_id) AS users_who_first_purchased_A_brand_in_march,
            COUNT(order_b.order_id) AS total_orders_in_march,
            SUM(TO_NUMBER(order_b.order_amount)) AS total_order_amount_in_march,
            SUM(TO_NUMBER(order_b.discount_amount)) AS total_discount_amount_in_march,
            SUM(TO_NUMBER(order_b.shipping_cost)) AS total_shipping_cost_in_march
        FROM
            order_b
        JOIN
            order_item_d ON order_b.order_id = order_item_d.order_id
        JOIN
            product_b ON order_item_d.product_id = product_b.product_id
        WHERE
            TO_CHAR(TO_DATE(order_b.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM') = '2024-03'
            AND product_b.brand_id = (SELECT brand_id FROM brand_b WHERE brand_name = 'Adidas')
            AND order_b.user_id IN (
                SELECT o.user_id
                FROM order_b o
                WHERE TO_CHAR(TO_DATE(o.order_date, 'YYYYMMDDHH24MISS'), 'YYYY-MM') = '2024-03'
                AND o.order_date = (
                    SELECT MIN(o2.order_date)
                    FROM order_b o2
                    WHERE o2.user_id = o.user_id
                )
                GROUP BY o.user_id
            )
    """.trimIndent()

        val query: Query = entityManager.createNativeQuery(sql)
        val rawResultList: List<*> = query.resultList

        return rawResultList.filterNotNull().mapNotNull {
            if (it is Array<*> && it.size == 5 &&
                it[0] is Number && it[1] is Number && it[2] is Number &&
                it[3] is Number && it[4] is Number
            ) {
                mapOf(
                    "usersWhoFirstPurchasedABrandInMarch" to (it[0] as Number).toLong(),
                    "totalOrdersInMarch" to (it[1] as Number).toLong(),
                    "totalOrderAmountInMarch" to (it[2] as Number).toLong(),
                    "totalDiscountAmountInMarch" to (it[3] as Number).toLong(),
                    "totalShippingCostInMarch" to (it[4] as Number).toLong()
                )
            } else {
                println("Unexpected result structure for getFirstTimeBuyersOfBrandInMarch: $it")
                null
            }
        }
    }

    override fun getUserSessionStatistics(): List<Map<String, Any>> {
        val sql = """
            WITH session_groups AS (
                SELECT
                    user_id,
                    TRUNC(TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS'), 'HH24') +
                    FLOOR(TO_NUMBER(TO_CHAR(TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS'), 'MI')) / 30) * NUMTODSINTERVAL(30, 'MINUTE') AS session_start_time,
                    TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS') AS access_ts,
                    page,
                    ROW_NUMBER() OVER (PARTITION BY user_id ORDER BY TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS')) -
                    ROW_NUMBER() OVER (
                        PARTITION BY user_id,
                            TRUNC(TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS'), 'HH24') +
                            FLOOR(TO_NUMBER(TO_CHAR(TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS'), 'MI')) / 30) * NUMTODSINTERVAL(30, 'MINUTE')
                        ORDER BY TO_TIMESTAMP(access_timestamp, 'YYYY-MM-DD HH24:MI:SS')
                    ) AS session_id
                FROM
                    page_view_l
                WHERE
                    REGEXP_LIKE(access_timestamp, '^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}${'$'}')
            ),
            session_metrics AS (
                SELECT
                    user_id,
                    TO_CHAR(access_ts, 'YYYY-MM') AS month,
                    COUNT(DISTINCT session_id) AS daily_sessions,
                    (
                        EXTRACT(DAY FROM (MAX(access_ts) - MIN(access_ts))) * 24 * 60 +
                        EXTRACT(HOUR FROM (MAX(access_ts) - MIN(access_ts))) * 60 +
                        EXTRACT(MINUTE FROM (MAX(access_ts) - MIN(access_ts))) +
                        EXTRACT(SECOND FROM (MAX(access_ts) - MIN(access_ts))) / 60
                    ) AS session_duration_minutes,
                    COUNT(DISTINCT page) AS total_unique_pages,
                    session_id
                FROM
                    session_groups
                GROUP BY
                    user_id, TO_CHAR(access_ts, 'YYYY-MM'), session_id
            ),
            session_stats AS (
                SELECT
                    user_id,
                    month,
                    SUM(daily_sessions) AS daily_session_count,
                    ROUND(AVG(session_duration_minutes), 2) AS avg_session_duration_minutes,
                    ROUND(SUM(total_unique_pages) / COUNT(session_id), 2) AS avg_unique_pages_per_session
                FROM
                    session_metrics
                GROUP BY
                    user_id, month
            )
            SELECT
                month,
                SUM(daily_session_count) AS total_daily_sessions,
                ROUND(AVG(avg_session_duration_minutes), 2) AS avg_monthly_session_duration,
                ROUND(AVG(avg_unique_pages_per_session), 2) AS avg_unique_pages_per_session
            FROM
                session_stats
            GROUP BY
                month
            ORDER BY
                month
    """.trimIndent()

        val query: Query = entityManager.createNativeQuery(sql)
        val rawResultList: List<*> = query.resultList

        return rawResultList.filterNotNull().mapNotNull {
            if (it is Array<*> && it.size == 4 &&
                it[0] is String && it[1] is Number && it[2] is Number && it[3] is Number
            ) {
                mapOf(
                    "month" to it[0] as String,
                    "totalDailySessions" to (it[1] as Number).toLong(),
                    "avgMonthlySessionDuration" to (it[2] as Number).toDouble(),
                    "avgUniquePagesPerSession" to (it[3] as Number).toDouble()
                )
            } else {
                println("Unexpected result structure for getUserSessionStatistics: $it")
                null
            }
        }
    }
}
