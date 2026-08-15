package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DrawnCard
import com.example.data.model.SpreadType
import com.example.data.repository.DeckRepository
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun SpreadVisualizer(
    spreadType: SpreadType,
    drawnCards: List<DrawnCard>,
    onCardClick: (DrawnCard) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("spread_visualizer"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = androidx.compose.foundation.BorderStroke(1.dp, ObsidianBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = spreadType.title,
                        color = StarlightGold,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${drawnCards.size} of ${spreadType.cardCount} Cards Drawn",
                        color = StarlightMuted,
                        fontSize = 11.sp
                    )
                }

                Surface(
                    color = ObsidianSurfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "✧ SACRED SPREAD ✧",
                        color = CelestialCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            when (spreadType) {
                SpreadType.DAILY_ALIGNMENT -> {
                    // Single card centered
                    drawnCards.firstOrNull()?.let { drawn ->
                        val card = DeckRepository.getCardById(drawn.cardId)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            TarotCardView(
                                card = card,
                                isReversed = drawn.isReversed,
                                positionTitle = drawn.positionName,
                                cardWidth = 160.dp,
                                cardHeight = 250.dp,
                                onCardClick = { onCardClick(drawn) },
                                onInfoClick = { onCardClick(drawn) }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = drawn.positionMeaning,
                                color = StarlightMuted,
                                fontSize = 11.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                SpreadType.THREE_CARD_TIMELINE,
                SpreadType.THREE_CARD_MIND_BODY_SPIRIT -> {
                    // Horizontal scroll or row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
                    ) {
                        drawnCards.forEach { drawn ->
                            val card = DeckRepository.getCardById(drawn.cardId)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(130.dp)
                            ) {
                                TarotCardView(
                                    card = card,
                                    isReversed = drawn.isReversed,
                                    positionTitle = drawn.positionName,
                                    cardWidth = 130.dp,
                                    cardHeight = 200.dp,
                                    onCardClick = { onCardClick(drawn) },
                                    onInfoClick = { onCardClick(drawn) }
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = drawn.positionMeaning,
                                    color = StarlightMuted,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }

                SpreadType.FIVE_CARD_CROSS -> {
                    // 5-Card cross geometric layout
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Top (The Crown)
                        drawnCards.getOrNull(3)?.let { drawn ->
                            SpreadCardSlot(drawn, 110.dp, 170.dp, onCardClick)
                        }

                        // Middle Row: (The Challenge / The Core / The Resolution)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            drawnCards.getOrNull(1)?.let { drawn ->
                                SpreadCardSlot(drawn, 100.dp, 155.dp, onCardClick)
                            }
                            drawnCards.getOrNull(0)?.let { drawn ->
                                SpreadCardSlot(drawn, 110.dp, 170.dp, onCardClick)
                            }
                            drawnCards.getOrNull(4)?.let { drawn ->
                                SpreadCardSlot(drawn, 100.dp, 155.dp, onCardClick)
                            }
                        }

                        // Bottom (The Root)
                        drawnCards.getOrNull(2)?.let { drawn ->
                            SpreadCardSlot(drawn, 110.dp, 170.dp, onCardClick)
                        }
                    }
                }

                SpreadType.WEEKLY_SYNTHESIS_SPREAD -> {
                    // 2x2 Grid or horizontal scroll
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        drawnCards.forEach { drawn ->
                            val card = DeckRepository.getCardById(drawn.cardId)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(130.dp)
                            ) {
                                TarotCardView(
                                    card = card,
                                    isReversed = drawn.isReversed,
                                    positionTitle = drawn.positionName,
                                    cardWidth = 130.dp,
                                    cardHeight = 200.dp,
                                    onCardClick = { onCardClick(drawn) },
                                    onInfoClick = { onCardClick(drawn) }
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = drawn.positionMeaning,
                                    color = StarlightMuted,
                                    fontSize = 10.sp,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }

                SpreadType.CELTIC_CROSS -> {
                    // Full 10 cards horizontal or structured scroll
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        drawnCards.forEachIndexed { index, drawn ->
                            val card = DeckRepository.getCardById(drawn.cardId)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(120.dp)
                            ) {
                                Text(
                                    text = "#${index + 1}",
                                    color = StarlightGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                TarotCardView(
                                    card = card,
                                    isReversed = drawn.isReversed,
                                    positionTitle = drawn.positionName,
                                    cardWidth = 120.dp,
                                    cardHeight = 185.dp,
                                    onCardClick = { onCardClick(drawn) },
                                    onInfoClick = { onCardClick(drawn) }
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = drawn.positionMeaning,
                                    color = StarlightMuted,
                                    fontSize = 9.sp,
                                    textAlign = TextAlign.Center,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SpreadCardSlot(
    drawn: DrawnCard,
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    onCardClick: (DrawnCard) -> Unit
) {
    val card = DeckRepository.getCardById(drawn.cardId)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TarotCardView(
            card = card,
            isReversed = drawn.isReversed,
            positionTitle = drawn.positionName,
            cardWidth = width,
            cardHeight = height,
            onCardClick = { onCardClick(drawn) },
            onInfoClick = { onCardClick(drawn) }
        )
    }
}
