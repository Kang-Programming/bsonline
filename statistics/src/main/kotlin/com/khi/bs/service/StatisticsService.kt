package com.khi.bs.service

interface StatisticsService {
    fun getMonthlyOrderStatistics(): List<Map<String, Any>>
    fun getMonthlyActiveUserCount(): List<Map<String, Any>>
    fun getUsersWithAvgOrderAbove100K(): List<Map<String, Any>>
    fun getFirstTimeBuyersOfBrandInMarch(): List<Map<String, Any>>
    fun getUserSessionStatistics(): List<Map<String, Any>>
}