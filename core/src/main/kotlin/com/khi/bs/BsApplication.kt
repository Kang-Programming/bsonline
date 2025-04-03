package com.khi.bs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
class BsApplication

fun main(args: Array<String>) {
	runApplication<BsApplication>(*args)
}
