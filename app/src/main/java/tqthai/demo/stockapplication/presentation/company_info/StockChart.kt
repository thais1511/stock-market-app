package tqthai.demo.stockapplication.presentation.company_info

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tqthai.demo.stockapplication.domain.model.CompanyInfo
import tqthai.demo.stockapplication.domain.model.IntradayInfo
import kotlin.math.roundToInt

@Composable
fun StockChart(
    infos: List<IntradayInfo> = emptyList(),
    modifier: Modifier = Modifier,
    graphColor: Color = Color.Green
) {
    //space of chart to corner
    val spacing = 100f
    val transparentingColor = remember {
        graphColor.copy(0.5f)
    }
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.close }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(infos) {
        infos.minOfOrNull { it.close }?.toInt() ?: 0
    }
    val density = LocalDensity.current
    val textPain = remember(density) {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(modifier = modifier) {
        val height = size.height
        val spacerPerHour = (size.width - spacing) / infos.size
        (0 until infos.size - 1 step 2).forEach { i ->
            val info = infos[i]
            val hour = info.date.hour

            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    hour.toString(),
                    spacing * i + spacerPerHour,
                    size.height - 5, // at the bottom with margin = 5
                    textPain
                )
            }
        }
        val priceStep = (upperValue - lowerValue) / 5f
        val topY = size.height - spacing
        val priceLabelCount = 5
        (0..priceLabelCount).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    (lowerValue + i * priceStep).toString(),
                    30f,
                    topY - i * size.height / priceLabelCount,
                    textPain
                )
            }
        }
        var lastX = 0f
        val strokePath = Path().apply {
            for (i in infos.indices) {
                val info = infos[i]
                val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                val leftRatio = (info.close - lowerValue) / (upperValue - lowerValue)
                val rightRatio = (nextInfo.close - lowerValue) / (upperValue - lowerValue)
                val x1 = spacing + i * spacerPerHour
                val y1 = size.height - spacing - (leftRatio * size.height).toFloat()
                val x2 = spacing + (i + 1) * spacerPerHour
                val y2 = size.height - spacing - (rightRatio * size.height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(x1, y1, lastX, (y1 + y2) / 2f)
            }
        }
        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(lastX, size.height - spacing)
                lineTo(spacing, size.height - spacing)
                close()
            }
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    transparentingColor,
                    Color.Transparent
                ),
                endY = size.height - spacing
            )
        )
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}