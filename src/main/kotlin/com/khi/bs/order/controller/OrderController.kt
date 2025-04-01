package com.khi.bs.order.controller

import com.khi.bs.order.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController(
    private val orderService: OrderService
) {
    @RequestMapping(value = ["/insertOrderInfo"], method = [RequestMethod.GET])
    fun insertOrderInfo(): ResponseEntity<String> {
        try {
            orderService.insertOrderInfo()
            return ResponseEntity.status(HttpStatus.OK).body("성공")
        }
        catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}