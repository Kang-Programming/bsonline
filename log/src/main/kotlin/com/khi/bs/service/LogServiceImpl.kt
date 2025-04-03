package com.khi.bs.service

import com.khi.bs.Util
import com.khi.bs.model.PageViewL
import com.khi.bs.repository.PageViewRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class LogServiceImpl(
    private val pageViewRepository : PageViewRepository,
    private val util : Util,
) : LogService {
    override fun insertLog() {
        if(pageViewRepository.count() > 0)
        {
            throw Exception("이미 데이터 기입이 되어 있습니다.")
        }
        val logData = util.getDataFile() // JSON 파일에서 데이터 읽기
        val logs = logData.mapNotNull { data ->
            PageViewL(
                userId = data["user_id"] ?: return@mapNotNull null,
                accessTimestamp = data["access_timestamp"],
                page = data["page"],
            )
        }
        pageViewRepository.saveAll(logs)
    }
}