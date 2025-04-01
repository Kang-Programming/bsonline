package com.khi.bs.log.controller

import com.khi.bs.log.service.LogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/log")
class LogController(
    private val logService: LogService
) {
    @RequestMapping(value = ["/insertLog"], method = [RequestMethod.GET])
    fun insertLog(): ResponseEntity<String> {
        try {
            logService.insertLog()
            return ResponseEntity.status(HttpStatus.OK).body("성공")
        }
        catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}