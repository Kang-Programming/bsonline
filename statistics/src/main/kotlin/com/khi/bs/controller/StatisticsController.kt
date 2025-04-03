package com.khi.bs.controller

import com.khi.bs.service.StatisticsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
class StatisticsController(
    private val statisticsService: StatisticsService
) {
    @RequestMapping(value = ["/getMonthlyOrderStatistics"], method = [RequestMethod.GET])
    fun getMonthlyOrderStatistics(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(statisticsService.getMonthlyOrderStatistics())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf("error" to (e.message ?: "Unknown error occurred"))
            )
        }
    }

    @RequestMapping(value = ["/getMonthlyActiveUserCount"], method = [RequestMethod.GET])
    fun getMonthlyActiveUserCount(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(statisticsService.getMonthlyActiveUserCount())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf("error" to (e.message ?: "Unknown error occurred"))
            )
        }
    }

    @RequestMapping(value = ["/getUsersWithAvgOrderAbove100K"], method = [RequestMethod.GET])
    fun getUsersWithAvgOrderAbove100K(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(statisticsService.getUsersWithAvgOrderAbove100K())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf("error" to (e.message ?: "Unknown error occurred"))
            )
        }
    }

    @RequestMapping(value = ["/getFirstTimeBuyersOfBrandInMarch"], method = [RequestMethod.GET])
    fun getFirstTimeBuyersOfBrandInMarch(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(statisticsService.getFirstTimeBuyersOfBrandInMarch())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf("error" to (e.message ?: "Unknown error occurred"))
            )
        }
    }

    @RequestMapping(value = ["/getUserSessionStatistics"], method = [RequestMethod.GET])
    fun getUserSessionStatistics(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(statisticsService.getUserSessionStatistics())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                mapOf("error" to (e.message ?: "Unknown error occurred"))
            )
        }
    }
}