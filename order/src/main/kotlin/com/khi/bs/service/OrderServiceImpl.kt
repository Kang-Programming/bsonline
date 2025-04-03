package com.khi.bs.service

import com.khi.bs.model.OrderB
import com.khi.bs.model.OrderItemD
import com.khi.bs.repository.OrderItemRepository
import com.khi.bs.repository.OrderRepository
import com.khi.bs.model.BrandB
import com.khi.bs.model.ProductB
import com.khi.bs.model.ProductStockD
import com.khi.bs.repository.BrandRepository
import com.khi.bs.repository.ProductRepository
import com.khi.bs.repository.ProductStockRepository
import com.khi.bs.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random
import org.springframework.context.annotation.Lazy

@Service
@Transactional
open class OrderServiceImpl (
    @Lazy private val orderRepository: OrderRepository,
    @Lazy private val orderItemRepository: OrderItemRepository,
    @Lazy private val userRepository: UserRepository,
    @Lazy private val productStockRepository: ProductStockRepository,
    @Lazy private val productRepository: ProductRepository,
    @Lazy private val brandRepository: BrandRepository
) : OrderService {
    override fun insertOrderInfo() {
        val users = userRepository.findAll()
        val allProductStocks = productStockRepository.findAll()
        val orderStatusList = listOf("결제전", "결제완료", "배송중", "배송완료")
        val paymentMethods = listOf("신용카드", "카카오페이", "네이버페이", "무통장입금")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")

        if (users.isEmpty() || allProductStocks.isEmpty()) {
            println("사용자 또는 상품 재고가 없습니다. 주문을 생성할 수 없습니다.")
            return
        }

        val orderList = mutableListOf<OrderB>()
        val orderItemList = mutableListOf<OrderItemD>()

        val startDate = LocalDate.of(2024, 1, 1)
        val endDate = LocalDate.now()
        val totalDays = ChronoUnit.DAYS.between(startDate, endDate).toInt()

        // 1. 각 날짜별 최소 1건 이상의 주문을 보장하기 위해 날짜 리스트를 생성
        val availableDates = (0..totalDays).map { startDate.plusDays(it.toLong()) }.shuffled()

        repeat(1000) { index ->
            val orderDate = availableDates[index % availableDates.size] // 날짜가 고르게 분포하도록 설정

            val randomHour = Random.nextInt(24)
            val randomMinute = Random.nextInt(60)
            val randomSecond = Random.nextInt(60)

            val orderDateTime = orderDate.atTime(randomHour, randomMinute, randomSecond)
            val formattedOrderDate = orderDateTime.format(dateFormatter)

            val user = users.random()
            val currentOrderItems = mutableListOf<OrderItemD>()
            val selectedProductStockIdsForOrder = mutableSetOf<String>()
            var orderBrand: BrandB? = null

            val itemCount = Random.nextInt(1, 5)
            repeat(itemCount) {
                var productStock: ProductStockD? = null
                var quantity: Int = 0
                var retryCount = 0
                val maxRetries = 10

                while (productStock == null && retryCount < maxRetries) {
                    val randomProductStock = allProductStocks.random()
                    val availableQty = randomProductStock.qty?.toIntOrNull() ?: 0
                    if (!selectedProductStockIdsForOrder.contains(randomProductStock.productStockId) && availableQty > 1) {
                        quantity = Random.nextInt(1, availableQty)
                        productStock = randomProductStock
                        selectedProductStockIdsForOrder.add(productStock.productStockId)
                    }
                    retryCount++
                }

                if (productStock != null) {
                    val productB = productRepository.findById(productStock.productId).orElse(null)
                    if (productB != null) {
                        val productPrice = (productB.price?.toIntOrNull() ?: 0) * quantity
                        val orderItem = OrderItemD(
                            orderItemId = UUID.randomUUID().toString(),
                            orderId = "", // OrderB가 저장된 후에 설정
                            productId = productStock.productId,
                            productStockId = productStock.productStockId,
                            qty = quantity.toString(),
                            productPrice = productPrice.toString(),
                            createdAt = LocalDateTime.now().format(dateFormatter)
                        )
                        currentOrderItems.add(orderItem)

                        if (orderBrand == null) {
                            orderBrand = brandRepository.findById(productB.brandId).orElse(null)
                        }
                    }
                }
            }

            if (currentOrderItems.isNotEmpty()) {
                val orderAmount = currentOrderItems.sumOf { it.productPrice?.toIntOrNull() ?: 0 }
                val discountAmount = (Random.nextInt(max(0, orderAmount / 100) + 1)) * 100
                val shippingCost = orderBrand?.shippingCost?.toIntOrNull() ?: 0

                val order = OrderB(
                    orderId = UUID.randomUUID().toString(),
                    userId = user.userId,
                    deliveryAddress = user.address ?: "",
                    orderStatus = orderStatusList.random(),
                    orderAmount = orderAmount.toString(),
                    discountAmount = discountAmount.toString(),
                    shippingCost = shippingCost.toString(),
                    orderDate = formattedOrderDate,
                    paymentMethod = paymentMethods.random(),
                    transactionId = UUID.randomUUID().toString(),
                    createdAt = LocalDateTime.now().format(dateFormatter)
                )
                orderList.add(order)

                currentOrderItems.forEach { it.orderId = order.orderId }
                orderItemList.addAll(currentOrderItems)
            }
        }
        orderRepository.saveAll(orderList)
        orderItemRepository.saveAll(orderItemList)
    }
}