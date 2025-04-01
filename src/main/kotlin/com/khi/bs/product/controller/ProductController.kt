package com.khi.bs.product.controller

import com.khi.bs.product.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService : ProductService
) {
    @RequestMapping(value = ["/insertProductInfo"], method = [RequestMethod.GET])
    fun insertProductInfo(): ResponseEntity<String> {
        try {
            productService.insertProductInfo()
            return ResponseEntity.status(HttpStatus.OK).body("성공")
        }
        catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}