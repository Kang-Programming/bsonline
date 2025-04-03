package com.khi.bs.service

import com.khi.bs.Util
import com.khi.bs.model.UserB
import com.khi.bs.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.SecretKey

@Service
@Transactional
open class UserServiceImpl(
    private val userRepository: UserRepository,
    private val util: Util,
) : UserService {
    override fun insertUserList() {
        if(userRepository.count() > 0)
        {
            throw Exception("이미 데이터 기입이 되어 있습니다.")
        }
        val dataFile: List<Map<String, String>> = util.getDataFile()
        val seenUserIdList = mutableSetOf<String>()
        val seenPhoneNumberList = mutableSetOf<String>()
        val emailDomains = listOf(
            "gmail.com",
            "yahoo.com",
            "hotmail.com",
            "outlook.com",
            "aol.com",
            "naver.com",
            "hanmail.net",
            "nate.com",
            "korea.com",
            "paran.com",
            "empal.com"
        )
        val random = Random() // Random 객체 생성
        // 사용자 정보를 하나씩 DB에 저장
        dataFile.forEach { data ->
            val userId = data["user_id"] ?: return@forEach // user_id가 없다면 넘어감

            // 이미 처리한 userId는 건너뛰기
            if (seenUserIdList.contains(userId)) {
                return@forEach // 이미 처리한 userId는 건너뛰기
            }

            // userId를 Set에 추가 (중복을 방지)
            seenUserIdList.add(userId)
            val originalPassword = util.getRandomPassword()
            val (hashedPassword, salt) = util.hashPasswordWithSalt(originalPassword)
            val privateNumber = util.createRandomPrivateNumber()
            val name = util.createRandomKoreanName()
            val secretKeyObject: SecretKey = util.createSecretKey() // SecretKey 객체 생성
            val encodedKey: String = Base64.getEncoder().encodeToString(secretKeyObject.encoded)
            var phoneNumber: String
            while (true) {
                val randomNumber = String.format("%08d", random.nextInt(100000000))
                phoneNumber = "010$randomNumber"
                if (!seenPhoneNumberList.contains(phoneNumber)) {
                    seenPhoneNumberList.add(phoneNumber)
                    break
                }
            }
            val user = UserB(
                userId = userId,
                password = hashedPassword,
                name = name,
                email = "${util.getRomanizeKoreanName(name)}${privateNumber.substring(2, 6)}@${emailDomains[random.nextInt(emailDomains.size)]}",
                phoneNumber = phoneNumber,
                address = util.createRandomKoreanAddress(),
                registrationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                lastLoginDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")),
                encryptedField1 = salt,
                encryptedField2 = encodedKey,
                encryptedField3 = util.encrypt(privateNumber, secretKeyObject),
            )
            userRepository.save(user)
        }
    }
}