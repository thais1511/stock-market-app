package tqthai.demo.stockapplication.presentation.company_info

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import tqthai.demo.stockapplication.domain.model.CompanyInfo
import tqthai.demo.stockapplication.domain.model.IntradayInfo

@Composable
fun StockChart(
     infos: List<IntradayInfo> = emptyList(),
     modifier: Modifier = Modifier,
     graphColor: Color = Color.Green
) {
    Text(text = "")
}