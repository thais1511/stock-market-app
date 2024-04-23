package tqthai.demo.stockapplication.data.csv

import com.opencsv.CSVReader
import tqthai.demo.stockapplication.data.mapper.toCompanyInfo
import tqthai.demo.stockapplication.data.mapper.toIntraInfo
import tqthai.demo.stockapplication.data.remote.CompanyInfoDto
import tqthai.demo.stockapplication.data.remote.IntradayInfoDto
import tqthai.demo.stockapplication.domain.model.IntradayInfo
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo>{
    override suspend fun parse(byteStream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(byteStream))
        return csvReader.readAll()
            .drop(1)
            .mapNotNull { line ->
                val timeStamp = line.getOrNull(0) ?: return@mapNotNull null
                val close = line.getOrNull(4) ?: return@mapNotNull null
                val dto = IntradayInfoDto(timeStamp, close = close.toDouble())
                dto.toIntraInfo()
            }
            .filter {
                it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth
            }
            .sortedBy {
                it.date.hour
            }
            .also {
                csvReader.close()
            }
    }
}