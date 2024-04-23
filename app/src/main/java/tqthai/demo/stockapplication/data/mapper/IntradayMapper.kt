package tqthai.demo.stockapplication.data.mapper

import android.os.Build
import tqthai.demo.stockapplication.data.remote.IntradayInfoDto
import tqthai.demo.stockapplication.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun IntradayInfoDto.toIntraInfo(): IntradayInfo{
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formater = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localdateTime = LocalDateTime.parse(timestamp, formater)

    return IntradayInfo(date = localdateTime, close = close)
}