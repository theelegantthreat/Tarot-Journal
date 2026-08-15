package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.JournalEntry
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.ElementAirColor
import com.example.ui.theme.ElementEarthColor
import com.example.ui.theme.ElementFireColor
import com.example.ui.theme.ElementWaterColor
import com.example.ui.theme.EncryptedGreen
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun SentimentWaveChart(
    entries: List<JournalEntry>,
    modifier: Modifier = Modifier
) {
    val sortedEntries = entries.sortedBy { it.timestamp }.takeLast(7)
    val wavePoints = if (sortedEntries.isNotEmpty()) {
        sortedEntries.map { it.emotionalWaveRating }
    } else {
        listOf(7.0f, 7.5f, 8.0f, 8.8f, 7.4f, 8.5f, 9.0f)
    }

    val avgWave = if (sortedEntries.isNotEmpty()) {
        sortedEntries.map { it.emotionalWaveRating }.average().toFloat()
    } else 8.1f

    val avgSentiment = if (sortedEntries.isNotEmpty()) {
        sortedEntries.map { it.sentimentScore }.average().toFloat()
    } else 0.75f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("sentiment_wave_chart"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ShowChart,
                        contentDescription = "Biorhythm Wave",
                        tint = StarlightGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Emotional Wave & Sentiment Tracking",
                        color = StarlightWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Surface(
                    color = EncryptedGreen.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(0.5.dp, EncryptedGreen)
                ) {
                    Text(
                        text = "Automated NLP",
                        color = EncryptedGreen,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Wave Canvas Graph
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(ObsidianSurfaceVariant)
                    .padding(8.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // Grid lines (1 to 10 scale)
                    val midY = h / 2f
                    drawLine(
                        color = ObsidianBorder.copy(alpha = 0.6f),
                        start = Offset(0f, midY),
                        end = Offset(w, midY),
                        strokeWidth = 1f
                    )

                    if (wavePoints.size > 1) {
                        val stepX = w / (wavePoints.size - 1).coerceAtLeast(1)
                        val path = Path()
                        val fillPath = Path()

                        wavePoints.forEachIndexed { i, rating ->
                            // Scale 1 to 10 onto canvas height inverted
                            val normY = 1.0f - ((rating - 1f) / 9f).coerceIn(0f, 1f)
                            val x = i * stepX
                            val y = normY * (h - 20f) + 10f

                            if (i == 0) {
                                path.moveTo(x, y)
                                fillPath.moveTo(x, h)
                                fillPath.lineTo(x, y)
                            } else {
                                path.lineTo(x, y)
                                fillPath.lineTo(x, y)
                            }

                            // Glowing point
                            drawCircle(
                                color = StarlightGold,
                                radius = 4.5f,
                                center = Offset(x, y)
                            )
                            drawCircle(
                                color = Color.White,
                                radius = 2f,
                                center = Offset(x, y)
                            )
                        }

                        fillPath.lineTo(w, h)
                        fillPath.close()

                        // Gradient fill under the curve
                        drawPath(
                            path = fillPath,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    StarlightGold.copy(alpha = 0.35f),
                                    AmethystPurple.copy(alpha = 0.15f),
                                    Color.Transparent
                                )
                            )
                        )

                        // Stroke line
                        drawPath(
                            path = path,
                            brush = Brush.horizontalGradient(
                                listOf(CelestialCyan, StarlightGold, AmethystPurple)
                            ),
                            style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Score Metrics Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MetricPill(
                    title = "Avg Emotional Wave",
                    value = "${"%.1f".format(avgWave)} / 10",
                    color = StarlightGold
                )
                MetricPill(
                    title = "Sentiment Polarity",
                    value = if (avgSentiment >= 0) "+${"%.2f".format(avgSentiment)}" else "%.2f".format(avgSentiment),
                    color = CelestialCyan
                )
                MetricPill(
                    title = "Alchemical Valence",
                    value = "Flow State",
                    color = AmethystPurple
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Elemental balance breakdown
            Text(
                text = "✦ ELEMENTAL CORRESPONDENCE BALANCE",
                color = StarlightMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ElementalProgress("🜂 Fire (Will)", 0.40f, ElementFireColor, Modifier.weight(1f))
                ElementalProgress("🜄 Water (Intuition)", 0.30f, ElementWaterColor, Modifier.weight(1f))
                ElementalProgress("🜁 Air (Mind)", 0.20f, ElementAirColor, Modifier.weight(1f))
                ElementalProgress("🜃 Earth (Lab)", 0.10f, ElementEarthColor, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun MetricPill(title: String, value: String, color: Color) {
    Surface(
        color = ObsidianSurfaceVariant,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(0.5.dp, ObsidianBorder)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, color = StarlightMuted, fontSize = 9.sp)
            Text(text = value, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ElementalProgress(label: String, progress: Float, color: Color, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = label, color = StarlightWhite, fontSize = 9.sp, maxLines = 1)
        Spacer(modifier = Modifier.height(2.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = color,
            trackColor = ObsidianSurfaceVariant
        )
    }
}
