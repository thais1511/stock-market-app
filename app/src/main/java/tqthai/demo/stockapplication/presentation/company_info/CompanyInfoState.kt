package tqthai.demo.stockapplication.presentation.company_info

import tqthai.demo.stockapplication.domain.model.CompanyInfo
import tqthai.demo.stockapplication.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val loading: Boolean = false,
    val error: String? = null
)