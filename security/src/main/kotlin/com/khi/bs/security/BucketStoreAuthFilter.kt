package com.khi.bs.security

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class BucketStoreAuthFilter(/*private val util: Util*/) /*: Filter*/ {

//    @Value("\${bucketstore.expected-value}") // application.properties에 설정할 기대값
//    private lateinit var expectedBucketStoreValue: String
//
//    @Value("\${bucketstore.encryption.key}") // application.properties에 설정할 암호화 키 (Base64 인코딩된 값이라고 가정)
//    private lateinit var encryptionKeyBase64: String
//
//    private val secretKey by lazy { util.getSecretKeyFromEncoded(encryptionKeyBase64) } // Util에 키 변환 함수 필요
//
//    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
//        val httpRequest = request as HttpServletRequest
//        val httpResponse = response as HttpServletResponse
//
//        println(">>>>>>>>>>>>>>>>>> ${httpRequest.requestURI}")
//        if (httpRequest.requestURI.contains("/swagger-ui") || httpRequest.requestURI.contains("/v3/api-docs")
//            || httpRequest.requestURI.contains("/favicon.ico") || httpRequest.requestURI.contains("/error")) {
//            chain.doFilter(request, response)
//            return
//        }
//
//        val bucketStoreHeader = httpRequest.getHeader("bucketstore")
//
//        println("🔍 Received bucketstore header: $bucketStoreHeader")
//
//        if (!bucketStoreHeader.isNullOrBlank()) {
//            chain.doFilter(request, response) // 요청 허용
//            val decryptedValue = util.decrypt(bucketStoreHeader, secretKey)
//            if (decryptedValue == expectedBucketStoreValue) {
//                chain.doFilter(request, response) // 요청 허용
//            } else {
//                httpResponse.status = HttpStatus.UNAUTHORIZED.value()
//                httpResponse.writer.write("Unauthorized: Invalid bucketstore header")
//                return
//            }
//        } else {
//            httpResponse.status = HttpStatus.UNAUTHORIZED.value()
//            httpResponse.writer.write("Unauthorized: Missing bucketstore header")
//            return
//        }
//    }
}