package com.khi.bs

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.time.LocalDate
import java.util.Random
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

@Component
class Util {

    fun getDataFile(): List<Map<String, String>> {
        val objectMapper = ObjectMapper()
        val inputStream: InputStream = this::class.java.getResourceAsStream("/sample_user_log.json")
            ?: throw IllegalArgumentException("파일을 찾을 수 없습니다: sample_user_log.json")

        val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
        val dataList = mutableListOf<Map<String, String>>()

        reader.useLines { lines ->
            lines.forEach { line ->
                try {
                    val data: Map<String, String> = objectMapper.readValue(line, object : TypeReference<Map<String, String>>() {})
                    dataList.add(data)
                } catch (e: Exception) {
                    // 각 줄 파싱 실패 시 로그를 남기거나 처리할 수 있습니다.
                    println("Error parsing line: $line - ${e.message}")
                }
            }
        }
        return dataList
    }
    /**
     * 비밀번호를 무작위로 생성합니다.
     * @param minLength 최소 비밀번호 길이
     * @param maxLength 최대 비밀번호 길이
     * @return 무작위로 생성된 비밀번호
     */
    fun getRandomPassword(minLength: Int = 8, maxLength: Int = 32): String {
        val passwordLength = Random().nextInt(maxLength - minLength + 1) + minLength

        val allowedChars = ('a'..'z') + ('A'..'Z') + ('0'..'9') + ('!'..'/') + (':'..'@') + ('['..'`') + ('{'..'~')
        val random = Random()
        val password = StringBuilder()

        for (i in 0 until passwordLength) {
            val randomIndex = random.nextInt(allowedChars.size)
            password.append(allowedChars[randomIndex])
        }

        return password.toString()
    }

    /**
     * 안전한 랜덤 솔트를 생성합니다.
     * @return 생성된 솔트 (바이트 배열)
     */
    private fun createSalt(saltLength: Int = 16): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(saltLength)
        random.nextBytes(salt)
        return salt
    }

    /**
     * 주어진 비밀번호와 솔트를 사용하여 SHA-256 해시 값을 생성합니다.
     * @param plainTextPassword 평문 비밀번호
     * @param salt 솔트 (바이트 배열)
     * @return 해시된 비밀번호 (바이트 배열)
     */
    private fun hashPassword(plainTextPassword: String, salt: ByteArray): ByteArray {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(salt) // 솔트를 사용하여 해시 업데이트
        return md.digest(plainTextPassword.toByteArray(Charsets.UTF_8)) // 비밀번호를 바이트 배열로 변환하여 해시 계산
    }

    /**
     * 비밀번호를 해시하고, 생성된 솔트와 해시된 비밀번호를 Base64 문자열로 반환합니다.
     * @param plainTextPassword 평문 비밀번호
     * @return Pair<해시된 비밀번호 (Base64), 솔트 (Base64)>
     */
    fun hashPasswordWithSalt(plainTextPassword: String): Pair<String, String> {
        val saltBytes = createSalt()
        val hashedPasswordBytes = hashPassword(plainTextPassword, saltBytes)
        val saltBase64 = Base64.getEncoder().encodeToString(saltBytes) // 솔트를 Base64 문자열로 인코딩
        val hashedPasswordBase64 = Base64.getEncoder().encodeToString(hashedPasswordBytes) // 해시된 비밀번호를 Base64 문자열로 인코딩
        return Pair(hashedPasswordBase64, saltBase64)
    }

    /**
     * 입력된 평문 비밀번호가 저장된 해시 값 및 솔트와 일치하는지 확인합니다.
     * @param plainTextPassword 입력된 평문 비밀번호
     * @param hashedPasswordBase64 저장된 해시된 비밀번호 (Base64 문자열)
     * @param saltBase64 저장된 솔트 (Base64 문자열)
     * @return 일치하면 true, 아니면 false
     */
    fun verifyPassword(plainTextPassword: String, hashedPasswordBase64: String, saltBase64: String): Boolean {
        val saltBytes = Base64.getDecoder().decode(saltBase64) // 저장된 솔트를 Base64 문자열에서 바이트 배열로 디코딩
        val hashedPasswordBytes = hashPassword(plainTextPassword, saltBytes) // 입력된 비밀번호와 저장된 솔트를 사용하여 해시 계산
        val hashedPasswordToCheckBase64 = Base64.getEncoder().encodeToString(hashedPasswordBytes) // 계산된 해시 값을 Base64 문자열로 인코딩
        return hashedPasswordToCheckBase64 == hashedPasswordBase64 // 계산된 해시 값과 저장된 해시 값 비교
    }

    /**
     * 무작위로 한국인 이름을 생성합니다.
     * @return 무작위로 생성된 한국인 이름
     */
    fun createRandomKoreanName(): String {
        val commonFamilyNames = listOf(
            "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "왕", "남궁", "지", "고", "유", "반", "공",
            "단", "어", "함", "골", "위", "갈", "방", "신", "전", "배", "빙", "석", "설", "손", "류", "연", "한",
            "소봉", "노", "안", "동", "만", "음", "뇌", "범", "해", "허", "아", "오", "홍", "군", "독고", "양",
            "문", "구", "제갈"
        )
        val firstSyllableLetters = listOf(
            "가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하",
            "거", "너", "더", "러", "머", "버", "서", "어", "저", "처", "커", "터", "퍼", "허",
            "고", "노", "도", "로", "모", "보", "소", "오", "조", "초", "코", "토", "포", "호",
            "구", "누", "두", "루", "무", "부", "수", "우", "주", "추", "쿠", "투", "푸", "후",
            "그", "느", "드", "르", "므", "브", "스", "으", "즈", "츠", "크", "트", "프", "흐",
            "기", "니", "디", "리", "미", "비", "시", "이", "지", "치", "키", "티", "피", "히"
        )
        val secondSyllableLetters = listOf(
            "가", "나", "다", "라", "마", "바", "사", "아", "자", "차", "카", "타", "파", "하",
            "건", "논", "돈", "론", "몬", "본", "손", "온", "존", "촌", "콘", "톤", "폰", "혼",
            "갈", "날", "달", "랄", "말", "발", "살", "알", "잘", "찰", "칼", "탈", "팔", "할",
            "감", "남", "담", "람", "맘", "밤", "삼", "암", "잠", "참", "캄", "탐", "팜", "함",
            "갑", "납", "답", "랍", "맙", "밥", "삽", "압", "잡", "찹", "캅", "탑", "팝", "합",
            "갓", "낫", "닷", "랏", "맛", "밧", "삿", "앗", "잣", "찻", "캇", "탓", "팟", "핫"
            // 더 많은 음절 추가 가능
        )

        val random = Random()
        val familyName = commonFamilyNames[random.nextInt(commonFamilyNames.size)]
        val nameLength = random.nextInt(2) + 1 // 이름은 1글자 또는 2글자

        val givenName = StringBuilder()
        givenName.append(firstSyllableLetters[random.nextInt(firstSyllableLetters.size)])
        if (nameLength == 2) {
            givenName.append(secondSyllableLetters[random.nextInt(secondSyllableLetters.size)])
        }

        return familyName + givenName.toString()
    }

    /**
     * 한국어 이름을 영문자로 변환합니다.
     * @param koreanName 변환할 한국어 이름
     * @return 변환된 영문 로마자
     */
    fun getRomanizeKoreanName(koreanName: String): String {
        val initialConsonants = listOf(
            "g", "kk", "n", "d", "tt", "r", "m", "b", "pp", "s", "ss", "", "j", "jj", "ch", "k", "t", "p", "h"
        )
        val vowels = listOf(
            "a", "ae", "ya", "yae", "eo", "e", "yeo", "ye", "o", "wa", "wae", "oe", "yo",
            "u", "wo", "we", "wi", "yu", "eu", "ui", "i"
        )
        val finalConsonants = listOf(
            "", "k", "kk", "ks", "n", "nj", "nh", "t", "l", "lg", "lm", "lb", "ls", "lt", "lp", "lh",
            "m", "b", "ps", "s", "ss", "ng", "j", "ch", "k", "t", "p", "h"
        )

        val initialMap = mapOf(
            'ㄱ' to 0, 'ㄲ' to 1, 'ㄴ' to 2, 'ㄷ' to 3, 'ㄸ' to 4, 'ㄹ' to 5, 'ㅁ' to 6, 'ㅂ' to 7, 'ㅃ' to 8,
            'ㅅ' to 9, 'ㅆ' to 10, 'ㅇ' to 11, 'ㅈ' to 12, 'ㅉ' to 13, 'ㅊ' to 14, 'ㅋ' to 15, 'ㅌ' to 16, 'ㅍ' to 17, 'ㅎ' to 18
        )
        val vowelMap = mapOf(
            'ㅏ' to 0, 'ㅐ' to 1, 'ㅑ' to 2, 'ㅒ' to 3, 'ㅓ' to 4, 'ㅔ' to 5, 'ㅕ' to 6, 'ㅖ' to 7,
            'ㅗ' to 8, 'ㅘ' to 9, 'ㅙ' to 10, 'ㅚ' to 11, 'ㅛ' to 12, 'ㅜ' to 13, 'ㅝ' to 14, 'ㅞ' to 15,
            'ㅟ' to 16, 'ㅠ' to 17, 'ㅡ' to 18, 'ㅢ' to 19, 'ㅣ' to 20
        )
        val finalMap = mapOf(
            ' ' to 0, 'ㄱ' to 1, 'ㄲ' to 2, 'ㄳ' to 3, 'ㄴ' to 4, 'ㄵ' to 5, 'ㄶ' to 6, 'ㄷ' to 7,
            'ㄹ' to 8, 'ㄺ' to 9, 'ㄻ' to 10, 'ㄼ' to 11, 'ㄽ' to 12, 'ㄾ' to 13, 'ㄿ' to 14, 'ㅀ' to 15,
            'ㅁ' to 16, 'ㅂ' to 17, 'ㅄ' to 18, 'ㅅ' to 19, 'ㅆ' to 20, 'ㅇ' to 21, 'ㅈ' to 22, 'ㅊ' to 23,
            'ㅋ' to 24, 'ㅌ' to 25, 'ㅍ' to 26, 'ㅎ' to 27
        )

        val result = StringBuilder()
        for (char in koreanName) {
            if (char in '가'..'힣') {
                val code = char.code - 0xAC00
                val finalConsonantIndex = code % 28
                val vowelIndex = ((code - finalConsonantIndex) / 28) % 21
                val initialConsonantIndex = (code - finalConsonantIndex - vowelIndex * 28) / (28 * 21)

                result.append(initialConsonants.getOrNull(initialConsonantIndex) ?: "")
                result.append(vowels.getOrNull(vowelIndex) ?: "")
                result.append(finalConsonants.getOrNull(finalConsonantIndex) ?: "")
            } else {
                result.append(char)
            }
        }
        return result.toString()
    }

    /**
     * 무작위로 주민번호를 생성합니다.
     * @return 무작위로 생성된 주민번호
     */
    fun createRandomPrivateNumber(): String {
        val random = Random()

        // 1. 생년월일 (YYMMDD) 생성
        val currentYear = LocalDate.now().year
        val startYear = 1900
        val endYear = currentYear - 1 // 현재 연도 이전까지 생성

        val year = startYear + random.nextInt(endYear - startYear + 1)
        val month = 1 + random.nextInt(12)
        val day = 1 + random.nextInt(LocalDate.of(year, month, 1).lengthOfMonth())

        val yearStr = String.format("%02d", year % 100)
        val monthStr = String.format("%02d", month)
        val dayStr = String.format("%02d", day)

        val birthDate = yearStr + monthStr + dayStr

        // 2. 성별 및 출생년도 구분 번호 (한 자리) 생성
        val isMale = random.nextBoolean() // true: 남성, false: 여성
        val centuryAndGenderDigit = when (year >= 2000) {
            true -> if (isMale) 3 else 4
            false -> if (isMale) 1 else 2
        }

        return birthDate + centuryAndGenderDigit
    }

    fun createSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    /**
     * 문자열을 암호화합니다.
     * @param plainText 암호화할 평문
     * @param secretKey 암호화에 사용할 비밀 키
     * @return 암호화된 문자열 (Base64 인코딩) 또는 null (오류 발생 시)
     */
    fun encrypt(plainText: String, secretKey: SecretKey): String? {
        return try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val iv = cipher.iv // Initialization Vector
            val encryptedBytes = cipher.doFinal(plainText.toByteArray(Charsets.UTF_8))

            // IV와 암호화된 데이터를 함께 Base64로 인코딩하여 반환
            val combined = iv + encryptedBytes
            Base64.getEncoder().encodeToString(combined)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * 암호화된 문자열을 복호화합니다.
     * @param cipherText 암호화된 문자열 (Base64 인코딩)
     * @param secretKey 복호화에 사용할 비밀 키 (암호화에 사용된 키와 동일해야 함)
     * @return 복호화된 평문 또는 null (오류 발생 시)
     */
    fun decrypt(cipherText: String, secretKey: SecretKey): String? {
        return try {
            val decodedBytes = Base64.getDecoder().decode(cipherText)
            // IV 추출 (AES/CBC/PKCS5Padding의 IV는 16바이트)
            val ivBytes = decodedBytes.copyOfRange(0, 16)
            val encryptedData = decodedBytes.copyOfRange(16, decodedBytes.size)

            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val ivSpec = IvParameterSpec(ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec)
            val decryptedBytes = cipher.doFinal(encryptedData)
            String(decryptedBytes, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getSecretKeyFromEncoded(encodedKey: String): SecretKey {
        val decodedKey = Base64.getDecoder().decode(encodedKey)
        return SecretKeySpec(decodedKey, "AES") // 암호화 알고리즘에 따라 변경
    }

    fun createRandomKoreanAddress(): String {
        val random = Random(System.currentTimeMillis()) // java.util.Random 사용
        val provinces = listOf(
            "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시",
            "경기도", "강원특별자치도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"
        )
        val cities = listOf(
            "강남구", "서초구", "해운대구", "수성구", "연수구", "동구", "유성구", "남구", "처인구", "춘천시", "청주시", "천안시",
            "전주시", "나주시", "안동시", "창원시", "서귀포시"
        )
        val neighborhoods = listOf(
            "역삼동", "반포동", "우동", "만촌동", "송도동", "학동", "관평동", "신정동", "모현읍", "효자동", "복대동", "신부동",
            "효자동", "이창동", "옥동", "상남동", "대정읍"
        )
        val streetTypes = listOf("대로", "로", "길")
        val streetNames = listOf(
            "테헤란", "언주", "해운대", "달구벌", "송도", "학동", "대덕", "중앙", "모현", "대학", "충청", "만남", "전라",
            "영산", "경북", "창원", "제주"
        )
        val apartmentNames = listOf(
            "현대아파트", "삼성아파트", "LG빌리지", "푸르지오", "자이", "래미안", "힐스테이트"
        )

        val province = provinces[random.nextInt(provinces.size)]
        val city = cities[random.nextInt(cities.size)]
        val neighborhood = neighborhoods[random.nextInt(neighborhoods.size)]
        val streetType = streetTypes[random.nextInt(streetTypes.size)]
        val streetName = streetNames[random.nextInt(streetNames.size)]
        val buildingNumber = 1 + random.nextInt(300) // 건물 번호는 1부터 300 사이로 임의 생성
        val postalCode = String.format("%05d", 1 + random.nextInt(99999)) // 5자리 우편번호 생성

        val useApartment = random.nextBoolean()
        val apartmentPart = if (useApartment) {
            val apartmentName = apartmentNames[random.nextInt(apartmentNames.size)]
            val apartmentNumber = 101 + random.nextInt(2000) // 동/호수는 임의 범위 설정
            " $apartmentName ${apartmentNumber}동 ${1 + random.nextInt(20)}층"
        } else {
            ""
        }

        return "$province $city $neighborhood $streetName$streetType $buildingNumber$apartmentPart ($postalCode)"
    }
}