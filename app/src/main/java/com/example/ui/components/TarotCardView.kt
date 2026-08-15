package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Element
import com.example.data.model.Suit
import com.example.data.model.TarotCard
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.ElementAirColor
import com.example.ui.theme.ElementEarthColor
import com.example.ui.theme.ElementFireColor
import com.example.ui.theme.ElementSpiritColor
import com.example.ui.theme.ElementWaterColor
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite

@Composable
fun TarotCardView(
    card: TarotCard,
    isReversed: Boolean = false,
    positionTitle: String? = null,
    cardWidth: Dp = 140.dp,
    cardHeight: Dp = 220.dp,
    isFaceUp: Boolean = true,
    onCardClick: (() -> Unit)? = null,
    onInfoClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var faceUpState by remember(isFaceUp) { mutableStateOf(isFaceUp) }

    val rotation by animateFloatAsState(
        targetValue = if (faceUpState) 180f else 0f,
        animationSpec = tween(durationMillis = 450, easing = FastOutSlowInEasing),
        label = "card_flip"
    )

    val isFrontVisible = rotation > 90f

    Card(
        modifier = modifier
            .width(cardWidth)
            .height(cardHeight)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(enabled = onCardClick != null || !faceUpState) {
                if (!faceUpState) {
                    faceUpState = true
                }
                onCardClick?.invoke()
            }
            .testTag("tarot_card_${card.id}"),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.5.dp,
            Brush.linearGradient(
                colors = listOf(StarlightGold, ObsidianBorder, AmethystPurple)
            )
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        if (!isFrontVisible) {
            // Card Back (Sacred Geometric Backing)
            CardBackView()
        } else {
            // Card Front (Rotated 180 on Y axis to appear normal)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                CardFrontView(
                    card = card,
                    isReversed = isReversed,
                    positionTitle = positionTitle,
                    onInfoClick = onInfoClick
                )
            }
        }
    }
}

@Composable
private fun CardBackView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF321A58),
                        ObsidianDeep,
                        Color(0xFF09040F)
                    )
                )
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        // Celestial sacred geometry frame
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF1F1138))
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "✧ ☽ 🜀 ☾ ✧",
                    color = StarlightGold.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "Sacred Seal",
                    tint = StarlightGold,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "ARCANA",
                    color = AmethystPurple,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 3.sp
                )
                Text(
                    text = "TAROT",
                    color = StarlightMuted,
                    fontSize = 9.sp,
                    letterSpacing = 2.sp
                )
            }
        }
    }
}

@Composable
private fun CardFrontView(
    card: TarotCard,
    isReversed: Boolean,
    positionTitle: String?,
    onInfoClick: (() -> Unit)?
) {
    val elementColor = when (card.element) {
        Element.FIRE -> ElementFireColor
        Element.WATER -> ElementWaterColor
        Element.AIR -> ElementAirColor
        Element.EARTH -> ElementEarthColor
        Element.SPIRIT -> ElementSpiritColor
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        ObsidianSurface,
                        ObsidianCard,
                        Color(0xFF140A24)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Header: Roman Numeral & Element Icon
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = card.romanNumeral,
                    color = StarlightGold,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )

                Surface(
                    color = elementColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(0.5.dp, elementColor.copy(alpha = 0.5f))
                ) {
                    Text(
                        text = "${card.element.symbol} ${card.element.label}",
                        color = elementColor,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    )
                }
            }

            // Center Symbol Artwork & Orientation Flip
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                elementColor.copy(alpha = 0.25f),
                                Color(0xFF1C1130),
                                ObsidianDeep
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.graphicsLayer {
                        if (isReversed) rotationZ = 180f
                    }
                ) {
                    Text(
                        text = getCardGlyph(card),
                        fontSize = 32.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = card.astrologyTransit.take(16),
                        color = StarlightMuted,
                        fontSize = 9.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Reversed Badge
                if (isReversed) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 2.dp),
                        color = Color(0xFFD32F2F).copy(alpha = 0.85f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "REVERSED",
                            color = Color.White,
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }
            }

            // Bottom Title & Keywords
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = card.name,
                    color = StarlightWhite,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (positionTitle != null) {
                    Text(
                        text = positionTitle,
                        color = CelestialCyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = card.keywords.take(2).joinToString(" • "),
                    color = StarlightMuted,
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        if (onInfoClick != null) {
            IconButton(
                onClick = onInfoClick,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Card Details",
                    tint = StarlightGold.copy(alpha = 0.7f),
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

fun getCardGlyph(card: TarotCard): String {
    return when (card.id) {
        0 -> "🂠" // The Fool
        1 -> "🝢" // The Magician
        2 -> "☽" // High Priestess
        3 -> "♀" // Empress
        4 -> "♈" // Emperor
        5 -> "🜂" // Hierophant
        6 -> "♊" // Lovers
        7 -> "♋" // Chariot
        8 -> "♌" // Strength
        9 -> "🝤" // Hermit
        10 -> "☸" // Wheel of Fortune
        11 -> "♎" // Justice
        12 -> "🝩" // Hanged Man
        13 -> "♏" // Death
        14 -> "🜄" // Temperance
        15 -> "♑" // Devil
        16 -> "⚡" // Tower
        17 -> "★" // Star
        18 -> "☾" // Moon
        19 -> "☉" // Sun
        20 -> "🕭" // Judgement
        21 -> "🜀" // World
        else -> when (card.suit) {
            Suit.WANDS -> "🜂"
            Suit.CUPS -> "🜄"
            Suit.SWORDS -> "🜁"
            Suit.PENTACLES -> "🜃"
            else -> "✧"
        }
    }
}
