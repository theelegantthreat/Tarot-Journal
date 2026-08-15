package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.TarotCard
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.ElementAirColor
import com.example.ui.theme.ElementEarthColor
import com.example.ui.theme.ElementFireColor
import com.example.ui.theme.ElementSpiritColor
import com.example.ui.theme.ElementWaterColor
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun CardDetailDialog(
    card: TarotCard,
    isReversed: Boolean = false,
    onDismiss: () -> Unit
) {
    val elementColor = when (card.element) {
        com.example.data.model.Element.FIRE -> ElementFireColor
        com.example.data.model.Element.WATER -> ElementWaterColor
        com.example.data.model.Element.AIR -> ElementAirColor
        com.example.data.model.Element.EARTH -> ElementEarthColor
        com.example.data.model.Element.SPIRIT -> ElementSpiritColor
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("card_detail_dialog"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = ObsidianSurface),
            border = BorderStroke(1.5.dp, Brush.linearGradient(listOf(StarlightGold, ObsidianBorder, AmethystPurple)))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with Close
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = card.romanNumeral,
                            color = StarlightGold,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = card.name,
                            color = StarlightWhite,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = StarlightMuted
                        )
                    }
                }

                if (isReversed) {
                    Surface(
                        color = Color(0xFFD32F2F).copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color(0xFFD32F2F)),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.padding(top = 6.dp)
                    ) {
                        Text(
                            text = "⚠ DRAWN IN REVERSED ORIENTATION",
                            color = Color(0xFFFF8A80),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Technical Correspondences Section
                Surface(
                    color = ObsidianCard,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, ObsidianBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "✦ TECHNICAL CORRESPONDENCES",
                            color = StarlightGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        CorrespondenceRow("Element", "${card.element.symbol} ${card.element.label}", elementColor)
                        CorrespondenceRow("Astrology Transit", card.astrologyTransit, CelestialCyan)
                        CorrespondenceRow("Numerology", card.numerologyMeaning, StarlightWhite)
                        CorrespondenceRow("Alchemical Stage", card.alchemicalCorrespondence, AmethystPurple)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Keywords
                Text(
                    text = "KEYWORDS",
                    color = StarlightMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    card.keywords.forEach { keyword ->
                        Surface(
                            color = ObsidianSurfaceVariant,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(0.5.dp, ObsidianBorder)
                        ) {
                            Text(
                                text = keyword,
                                color = StarlightGold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))
                HorizontalDivider(color = ObsidianBorder)
                Spacer(modifier = Modifier.height(14.dp))

                // Meanings
                MeaningSection(
                    title = "Upright Traditional Meaning",
                    content = card.uprightMeaning,
                    titleColor = StarlightGold,
                    isHighlighted = !isReversed
                )

                Spacer(modifier = Modifier.height(10.dp))

                MeaningSection(
                    title = "Reversed Meaning",
                    content = card.reversedMeaning,
                    titleColor = Color(0xFFFF8A80),
                    isHighlighted = isReversed
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Visual Snapshot Symbols
                Text(
                    text = "VISUAL SNAPSHOT & SYMBOLS",
                    color = CelestialCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = card.symbolDescription,
                    color = StarlightWhite,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Real-World Integration & Advice
                Surface(
                    color = Color(0xFF1E1533),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.4f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = "⚡ REAL-WORLD & LAB INTEGRATION",
                            color = StarlightGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = card.advice,
                            color = StarlightWhite,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = StarlightGold, contentColor = ObsidianDeep),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close Card Analysis", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun CorrespondenceRow(label: String, value: String, valueColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = StarlightMuted,
            fontSize = 12.sp
        )
        Text(
            text = value,
            color = valueColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun MeaningSection(
    title: String,
    content: String,
    titleColor: Color,
    isHighlighted: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isHighlighted) ObsidianSurfaceVariant else Color.Transparent)
            .padding(if (isHighlighted) 10.dp else 0.dp)
    ) {
        Text(
            text = title,
            color = titleColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            color = if (isHighlighted) StarlightWhite else StarlightMuted,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )
    }
}
