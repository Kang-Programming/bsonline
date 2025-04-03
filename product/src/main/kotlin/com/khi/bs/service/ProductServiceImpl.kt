package com.khi.bs.service

import com.khi.bs.model.BrandB
import com.khi.bs.model.ProductB
import com.khi.bs.model.ProductOptionD
import com.khi.bs.model.ProductStockD
import com.khi.bs.repository.BrandRepository
import com.khi.bs.repository.ProductOptionRepository
import com.khi.bs.repository.ProductRepository
import com.khi.bs.repository.ProductStockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.math.min

@Service
@Transactional
open class ProductServiceImpl(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productOptionRepository: ProductOptionRepository,
    private val productStockRepository: ProductStockRepository
) : ProductService {
    override fun insertProductInfo() {
        if(brandRepository.count() == 0.toLong())
        {
            val sampleBrandNameList = listOf(
                "Adidas", "Burberry", "Calvin Klein", "Diesel", "Esprit", "Fendi", "Gucci", "Hermès", "INC International Concepts",
                "J.Crew", "Kenneth Cole", "Lacoste", "Mango", "Nike", "Old Navy", "Prada", "Quiksilver",
                "Ralph Lauren", "Supreme", "Tommy Hilfiger", "Uniqlo", "Versace", "Wrangler", "XOXO", "Yves Saint Laurent",
                "Zara"
            )
            val brandMap = sampleBrandNameList.groupBy { it.first().uppercaseChar() }
            val descriptionMap = mapOf(
                'A' to "스포츠웨어의 혁신을 이끄는 글로벌 리딩 브랜드입니다.",
                'B' to "고급스러운 디자인과 클래식한 감성을 담은 영국 대표 브랜드입니다.",
                'C' to "심플하면서도 세련된 스타일을 추구하는 세계적인 패션 브랜드입니다.",
                'D' to "데님을 기반으로 자유롭고 트렌디한 스타일을 선보이는 브랜드입니다.",
                'E' to "젊고 활기찬 감각의 캐주얼 의류를 제공합니다.",
                'F' to "이탈리아 럭셔리 브랜드로, 독특하고 예술적인 디자인이 특징입니다.",
                'G' to "이탈리아를 대표하는 명품 브랜드로, 고급스러움의 대명사입니다.",
                'H' to "프랑스 럭셔리 브랜드로, 우아하고 세련된 스타일을 선보입니다.",
                'I' to "다양한 스타일을 아우르는 현대적인 감각의 패션 브랜드입니다.",
                'J' to "세련된 디자인과 좋은 품질의 캐주얼 의류를 제공합니다.",
                'K' to "모던하고 시크한 스타일을 제안하는 미국의 대표적인 패션 브랜드입니다.",
                'L' to "프랑스 스포츠웨어 브랜드로, 기능성과 스타일을 겸비했습니다.",
                'M' to "스페인 대표 패스트 패션 브랜드로, 최신 트렌드를 빠르게 반영합니다.",
                'N' to "혁신적인 기술력과 스타일리시한 디자인을 자랑하는 글로벌 스포츠 브랜드입니다.",
                'O' to "합리적인 가격에 트렌디한 캐주얼 의류를 제공하는 브랜드입니다.",
                'P' to "이탈리아 럭셔리 브랜드로, 고급스러운 소재와 혁신적인 디자인이 특징입니다.",
                'Q' to "서핑 및 액티브 스포츠웨어 분야를 선도하는 브랜드입니다.",
                'R' to "아메리칸 클래식 스타일을 대표하는 글로벌 패션 브랜드입니다.",
                'S' to "스트리트 패션을 대표하는 아이코닉한 브랜드입니다.",
                'T' to "편안함과 스타일을 모두 갖춘 글로벌 캐주얼 브랜드입니다.",
                'U' to "개성 있고 트렌디한 젊은 세대를 위한 패션 브랜드입니다.",
                'V' to "이탈리아 럭셔리 브랜드로, 화려하고 독창적인 디자인이 특징입니다.",
                'W' to "편안하고 실용적인 아웃도어 스타일의 의류를 제공합니다.",
                'X' to "여성스러움을 강조한 트렌디하고 스타일리시한 의류 브랜드입니다.",
                'Y' to "프랑스를 대표하는 럭셔리 브랜드로, 섬세하고 우아한 디자인이 특징입니다.",
                'Z' to "최신 트렌드를 빠르게 반영하는 글로벌 패스트 패션 브랜드입니다."
            )
            val possibleShippingCosts = (0..40).map { it * 500 }.toMutableList()
            possibleShippingCosts.shuffle()
            var shippingCostIndex = 0
            for (char in 'A'..'Z') {
                val brandsStartingWithChar = brandMap[char]
                if (!brandsStartingWithChar.isNullOrEmpty()) {
                    val brandName = brandsStartingWithChar.first()
                    if (!brandRepository.existsByBrandName(brandName)) {
                        val description = descriptionMap[char] ?: "${brandName}는 다양한 스타일의 의류를 제공하는 대표적인 브랜드입니다."
                        val shippingCost = if (shippingCostIndex < possibleShippingCosts.size) {
                            possibleShippingCosts[shippingCostIndex++]
                        } else {
                            (1000..5000).random()
                        }
                        val brand = BrandB(
                            brandName = brandName,
                            shippingCost = "$shippingCost",
                            description = description,
                        )
                        brandRepository.save(brand)
                    } else {
                        println("$brandName 브랜드는 이미 존재합니다.")
                    }
                } else {
                    println("${char}로 시작하는 브랜드가 없습니다.")
                }
            }
        }
        val brands = brandRepository.findAll()
        val random = Random()
        val productTypes = listOf(
            "티셔츠", "맨투맨", "후드티", "셔츠", "블라우스", "바지", "청바지", "스커트", "원피스", "자켓",
            "코트", "점퍼", "조끼", "가디건", "니트", "스웨터", "레깅스", "트레이닝복", "운동화", "구두"
        )
        val descriptions = listOf(
            "편안한 착용감의 기본 아이템입니다.", "트렌디한 디자인의 인기 상품입니다.", "활동적인 날에 잘 어울리는 제품입니다.",
            "세련된 스타일을 연출해 보세요.", "부드러운 소재로 제작되었습니다.", "다양한 코디에 활용하기 좋습니다.",
            "캐주얼한 매력을 더해줍니다.", "특별한 날을 위한 완벽한 선택입니다.", "따뜻하고 포근한 느낌을 선사합니다.",
            "가볍고 활동성이 뛰어납니다."
        )
        val categories = listOf(
            "상의", "하의", "아우터", "원피스/세트", "신발", "액세서리",
            "가방", "모자", "양말", "속옷", "수영복", "운동복", "파자마", "스포츠웨어", "정장",
            "스커트", "청바지", "니트웨어", "가디건", "조끼", "점퍼", "코트", "레깅스", "트레이닝복", "등산복"
        )
        val colors = listOf(
            "Black", "White", "Red", "Blue", "Green", "Yellow", "Gray", "Navy", "Beige", "Pink",
            "Orange", "Purple", "Brown", "Sky Blue", "Olive", "Mint", "Lavender", "Burgundy", "Charcoal", "Khaki",
            "Turquoise", "Coral", "Ivory", "Gold", "Silver", "Teal", "Magenta", "Lime", "Peach", "Cyan"
        )
        val sizes = listOf("XS", "S", "M", "L", "XL", "XXL", "Free")
        val productsToGenerate = 100
        val createdProductsCount = productRepository.count().toInt()
        val neededProducts = productsToGenerate - createdProductsCount
        if (neededProducts <= 0) {
            println("이미 ${productsToGenerate}개 이상의 상품이 존재합니다.")
            return
        }
        val createdProductIds = mutableSetOf<String>() // 생성된 상품 ID 추적 (필요 시)

        // 단계 1: 각 브랜드에 최소 1개의 상품 할당
        val shuffledBrands = brands.shuffled()
        val initialAllocationCount = min(neededProducts, shuffledBrands.size)

        println("Starting Phase 1: Allocating $initialAllocationCount products (one per brand)...")
        for (i in 0 until initialAllocationCount) {
            val brandForLoop1 = shuffledBrands[i]
            var savedProductForLoop1: ProductB? = null

            // === 인라인된 상품 생성 로직 시작 ===
            try {
                val productType = productTypes.random()
                val productName = "${brandForLoop1.brandName} $productType"
                val category = categories.random()
                val baseColor = colors.random()
                val description = descriptions.random()
                val productCode = "${category}-${baseColor}-${UUID.randomUUID().toString().substring(0, 8).uppercase()}" // "P-${UUID.randomUUID().toString().substring(0, 8).uppercase()}"
                val basePrice = (random.nextInt(491) + 100) * 100 // 10000 ~ 590000
                val productToSave = ProductB(
                    productCode = productCode,
                    brandId = brandForLoop1.brandId ?: throw IllegalStateException("Brand ID cannot be null for ${brandForLoop1.brandName}"),
                    productName = productName,
                    category = category,
                    color = baseColor,
                    description = description,
                    price = basePrice.toString(),
                )
                savedProductForLoop1 = productRepository.save(productToSave)
                println("  [Phase 1] Created product: ${savedProductForLoop1.productName} (ID: ${savedProductForLoop1.productId})")

            } catch (e: Exception) {
                println("  [Phase 1] Error creating product for brand ${brandForLoop1.brandName}: ${e.message}")
                continue // 다음 브랜드로 넘어감
            }
            // === 인라인된 상품 생성 로직 끝 ===

            // 상품이 성공적으로 저장된 경우에만 옵션/재고 생성 진행
            if (savedProductForLoop1 != null) {
                createdProductIds.add(savedProductForLoop1.productId!!) // Null 아님을 확신하고 추가

                // === 인라인된 옵션/재고 생성 로직 시작 ===
                try {
                    val productId = savedProductForLoop1.productId // 위에서 non-null 확신
                    println("    [Phase 1] Creating options/stock for product ID: $productId...")

                    // 1. 색상 옵션 생성 (1~3개 랜덤)
                    val numColorOptions = random.nextInt(3) + 1
                    val selectedColors = colors.shuffled().take(numColorOptions)

                    for (color in selectedColors) {
                        val additionalPrice = if (random.nextDouble() < 0.3) {
                            ((10..100).random() * 100).toString()
                        } else {
                            null
                        }

                        val colorOption = ProductOptionD(
                            productId = productId,
                            optionName = "Color",
                            optionValue = color,
                            additionalPrice = additionalPrice
                        )
                        val savedColorOption = productOptionRepository.save(colorOption)
                        val optionId = savedColorOption.productOptionId ?: throw IllegalStateException("Option ID cannot be null after save")

                        // 2. 해당 색상 옵션에 대한 사이즈별 재고 생성 (1~5개 사이즈 랜덤)
                        val numSizeStock = random.nextInt(min(5, sizes.size)) + 1
                        val selectedSizes = sizes.shuffled().take(numSizeStock)

                        for (size in selectedSizes) {
                            val stock = ProductStockD(
                                productId = productId,
                                productOptionId = optionId,
                                productSize = size,
                                qty = random.nextInt(101).toString()
                            )
                            productStockRepository.save(stock)
                        }
                        // println("      [Phase 1] Added ${selectedSizes.size} sizes for color option: $color")
                    }
                    println("    [Phase 1] Finished options/stock for product ID: $productId")

                } catch (e: Exception) {
                    println("    [Phase 1] Error creating options/stock for product ${savedProductForLoop1.productName}: ${e.message}")
                    // 옵션/재고 생성 실패 시 다음 상품으로 넘어감 (이미 상품은 생성됨)
                }
                // === 인라인된 옵션/재고 생성 로직 끝 ===
            }
        } // 단계 1 반복 종료

        // 단계 2: 나머지 필요한 상품을 랜덤 브랜드에 할당
        val remainingNeeded = neededProducts - createdProductIds.size // 실제 생성된 상품 수 기준으로 계산
        println("\nStarting Phase 2: Allocating remaining ${remainingNeeded} products randomly...")

        for (i in 0 until remainingNeeded) {
            val randomBrandForLoop2 = brands.random()
            var savedProductForLoop2: ProductB? = null

            // === 인라인된 상품 생성 로직 시작 ===
            try {
                val productType = productTypes.random()
                val productName = "${randomBrandForLoop2.brandName} ${productType}"
                val category = categories.random()
                val baseColor = colors.random()
                val description = descriptions.random()
                val productCode = "${category}-${baseColor}-${UUID.randomUUID().toString().substring(0, 8).uppercase()}" // "P-${UUID.randomUUID().toString().substring(0, 8).uppercase()}"
                val basePrice = (random.nextInt(491) + 100) * 100 // 10000 ~ 590000
                val productToSave = ProductB(
                    productCode = productCode,
                    brandId = randomBrandForLoop2.brandId ?: throw IllegalStateException("Brand ID cannot be null for ${randomBrandForLoop2.brandName}"),
                    productName = productName,
                    category = category,
                    color = baseColor,
                    description = description,
                    price = basePrice.toString(),
                )
                savedProductForLoop2 = productRepository.save(productToSave)
                println("  [Phase 2] Created product: ${savedProductForLoop2.productName} (ID: ${savedProductForLoop2.productId}) for brand ${randomBrandForLoop2.brandName}")

            } catch (e: Exception) {
                println("  [Phase 2] Error creating product for random brand ${randomBrandForLoop2.brandName}: ${e.message}")
                continue // 다음 상품 생성 시도
            }
            // === 인라인된 상품 생성 로직 끝 ===

            createdProductIds.add(savedProductForLoop2.productId)

            // === 인라인된 옵션/재고 생성 로직 시작 ===
            try {
                val productId = savedProductForLoop2.productId
                println("    [Phase 2] Creating options/stock for product ID: $productId...")

                // 1. 색상 옵션 생성 (1~3개 랜덤)
                val numColorOptions = random.nextInt(3) + 1
                val selectedColors = colors.shuffled().take(numColorOptions)

                for (color in selectedColors) {
                    val additionalPrice = if (random.nextDouble() < 0.3) {
                        ((10..100).random() * 100).toString()
                    } else {
                        null
                    }

                    val colorOption = ProductOptionD(
                        productId = productId,
                        optionName = "Color",
                        optionValue = color,
                        additionalPrice = additionalPrice
                    )
                    val savedColorOption = productOptionRepository.save(colorOption)
                    val optionId = savedColorOption.productOptionId ?: throw IllegalStateException("Option ID cannot be null after save")

                    // 2. 해당 색상 옵션에 대한 사이즈별 재고 생성 (1~5개 사이즈 랜덤)
                    val numSizeStock = random.nextInt(min(5, sizes.size)) + 1
                    val selectedSizes = sizes.shuffled().take(numSizeStock)

                    for (size in selectedSizes) {
                        val stock = ProductStockD(
                            productId = productId,
                            productOptionId = optionId,
                            productSize = size,
                            qty = random.nextInt(101).toString()
                        )
                        productStockRepository.save(stock)
                    }
                    // println("      [Phase 2] Added ${selectedSizes.size} sizes for color option: $color")
                }
                println("    [Phase 2] Finished options/stock for product ID: $productId")

            } catch (e: Exception) {
                println("    [Phase 2] Error creating options/stock for product ${savedProductForLoop2.productName}: ${e.message}")
            }
            // === 인라인된 옵션/재고 생성 로직 끝 ===
        } // 단계 2 반복 종료

        val finalProductCount = productRepository.count().toInt()
        println("\nData generation finished. Total products in DB: ${finalProductCount}. Products created/processed in this run: ${createdProductIds.size}")
    }
}