package com.khi.bs.user.controller

import com.khi.bs.user.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @RequestMapping(value = ["/insertUserList"], method = [RequestMethod.GET])
    fun insertUserList(): ResponseEntity<String> {
        try {
            userService.insertUserList()
            return ResponseEntity.status(HttpStatus.OK).body("성공")
        }
        catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
    }
}