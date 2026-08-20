package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.DrawnCard
import com.example.data.model.MoonPhase
import com.example.data.model.TarotCard
import com.example.data.repository.DeckRepository
import com.example.data.util.LunarInfo
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.DarkPurple
import com.example.ui.theme.DeepAmethyst
import com.example.ui.theme.ElementFireColor
import com.example.ui.theme.EmeraldSage
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardSecondary
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.RoseAura
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightSubtle
import com.example.ui.theme.StarlightWhite

/**
 * Lunar Biorhythm Tracker & Reading Influence Composable.
 * Displays the current moon phase, illumination, and its specific energetic &
 * alchemical influence on today's tarot reading.
 */
@Composable
fun LunarBiorhythmReadingInfluenceTracker(
    lunarInfo: LunarInfo,
    activeCard: TarotCard? = null,
    isReversed: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(true) }

    val readingInfluence = remember(lunarInfo.phase, activeCard, isReversed) {
        deriveLunarTarotInfluence(lunarInfo, activeCard, isReversed)
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("lunar_biorhythm_reading_influence_tracker"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header: Title Row followed by illumination badge on next row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.NightsStay,
                    contentDescription = "Lunar Phase",
                    tint = StarlightGold,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "LUNAR BIORHYTHM & TAROT INFLUENCE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = StarlightGold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Sub-row with Illumination Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = ObsidianSurfaceVariant,
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(0.5.dp, StarlightGold.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(StarlightGold)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${lunarInfo.illuminationPercentage}% ILLUM",
                            color = StarlightWhite,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Phase Showcase: Moon Orb + Phase Info + Biorhythm Cycle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Radiant Moon Orb
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    GoldGlow.copy(alpha = 0.95f),
                                    AmethystPurple.copy(alpha = 0.5f),
                                    DarkPurple
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = lunarInfo.phaseSymbol,
                        fontSize = 34.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lunarInfo.phaseName,
                        color = StarlightWhite,
                        fontSize = 17.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "${lunarInfo.zodiacSign} • ${lunarInfo.zodiacElement}",
                        color = CelestialCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "Day ${String.format(java.util.Locale.US, "%.1f", lunarInfo.moonAgeDays)} • ${if (lunarInfo.isWaxing) "Waxing ↗" else "Waning ↘"}",
                        color = StarlightMuted,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Bar for Synodic Cycle
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                LinearProgressIndicator(
                    progress = { lunarInfo.cycleProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = StarlightGold,
                    trackColor = ObsidianSurfaceVariant,
                    strokeCap = StrokeCap.Round
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Reading Influence Highlight Card
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("lunar_reading_influence_card"),
                color = ObsidianCardSecondary,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.5.dp, GoldGlow.copy(alpha = 0.4f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    // Header of the Influence Section
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = StarlightGold,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Influence on Today's Reading",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = StarlightGold
                            )
                        }

                        if (activeCard != null) {
                            Surface(
                                color = DeepAmethyst.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(4.dp),
                                border = BorderStroke(0.5.dp, AmethystPurple.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = "${activeCard.name}${if (isReversed) " (Rev)" else ""}",
                                    color = AmethystPurple,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Primary Synthesized Influence Text
                    Text(
                        text = readingInfluence.synthesizedReadingGuidance,
                        fontSize = 12.sp,
                        color = StarlightWhite,
                        lineHeight = 17.sp,
                        fontWeight = FontWeight.Normal
                    )

                    AnimatedVisibility(visible = isExpanded) {
                        Column(modifier = Modifier.padding(top = 10.dp)) {
                            HorizontalDivider(
                                color = ObsidianBorder,
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            // 1. Energetic Modulator
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Psychology,
                                    contentDescription = null,
                                    tint = CelestialCyan,
                                    modifier = Modifier
                                        .size(14.dp)
                                        .padding(top = 1.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "Tarot Interpretation Lens",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CelestialCyan
                                    )
                                    Text(
                                        text = readingInfluence.tarotModulationNote,
                                        fontSize = 10.sp,
                                        color = StarlightMuted,
                                        lineHeight = 14.sp
                                    )
                                }
                            }

                            // 2. Action Advice for Today's Spread
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = StarlightGold,
                                    modifier = Modifier
                                        .size(14.dp)
                                        .padding(top = 1.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "Lunar Action Directive",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = StarlightGold
                                    )
                                    Text(
                                        text = readingInfluence.actionDirective,
                                        fontSize = 10.sp,
                                        color = StarlightMuted,
                                        lineHeight = 14.sp
                                    )
                                }
                            }

                            // 3. Alchemical & Emotional Resonance
                            Row(
                                modifier = Modifier.padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Science,
                                    contentDescription = null,
                                    tint = EmeraldSage,
                                    modifier = Modifier
                                        .size(14.dp)
                                        .padding(top = 1.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "Alchemical Timing & Resonance",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = EmeraldSage
                                    )
                                    Text(
                                        text = "${lunarInfo.alchemicalLabTiming} (${lunarInfo.emotionalResonance})",
                                        fontSize = 10.sp,
                                        color = StarlightMuted,
                                        lineHeight = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Expand/Collapse Details Toggle
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .clickable { isExpanded = !isExpanded }
                    .padding(vertical = 4.dp, horizontal = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isExpanded) "Show Less" else "Expand Reading Synergy Insights",
                    color = StarlightMuted,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = StarlightMuted,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

/**
 * Data holder for synthesized influence calculation.
 */
private data class LunarTarotInfluence(
    val synthesizedReadingGuidance: String,
    val tarotModulationNote: String,
    val actionDirective: String
)

/**
 * Computes how the current moon phase specifically colors and modulates the tarot reading.
 */
private fun deriveLunarTarotInfluence(
    lunarInfo: LunarInfo,
    activeCard: TarotCard?,
    isReversed: Boolean
): LunarTarotInfluence {
    val phase = lunarInfo.phase
    val cardName = activeCard?.name ?: "Today's Drawn Card"

    val baseModulation = when (phase) {
        MoonPhase.NEW_MOON -> "The New Moon amplifies beginnings and internal seeds. The reading highlights subconscious potential and unspoken intentions."
        MoonPhase.WAXING_CRESCENT -> "The Waxing Crescent accelerates early momentum. Any card drawn emphasizes taking bold initial steps, building prototypes, and trusting fresh sparks."
        MoonPhase.FIRST_QUARTER -> "The First Quarter creates constructive tension. Cards in this phase spotlight decision gates, overcoming friction, and resolving internal doubt."
        MoonPhase.WAXING_GIBBOUS -> "The Waxing Gibbous focuses on refinement and technical iteration. Cards highlight meticulous adjustments, craftsmanship, and perfecting your alignment."
        MoonPhase.FULL_MOON -> "The Full Moon offers maximum illumination and revelation. Hidden dynamics are exposed in the spread, magnifying polarity and peak results."
        MoonPhase.WANING_GIBBOUS -> "The Waning Gibbous fosters gratitude, teaching, and sharing insights. The spread suggests synthesizing discoveries and mentoring others."
        MoonPhase.LAST_QUARTER -> "The Last Quarter demands purification, detachment, and energetic decluttering. Cards highlight what must be released to make room for renewal."
        MoonPhase.WANING_CRESCENT -> "The Waning Crescent asks for quiet surrender and reflection. The reading guides restorative rest, contemplation, and shadow integration."
    }

    val synthesized = if (activeCard != null) {
        val orientation = if (isReversed) "reversed" else "upright"
        val element = activeCard.element.label
        "Under the ${phase.phaseName} (${lunarInfo.zodiacSign}), $cardName ($orientation, $element) acts as your primary energetic catalyst. " +
                "The lunar current directs this card's wisdom toward ${lunarInfo.biorhythmEnergyFocus.lowercase()}."
    } else {
        "Under the ${phase.phaseName} (${lunarInfo.zodiacSign}), today's tarot reading acts as a mirror for ${lunarInfo.biorhythmEnergyFocus.lowercase()}."
    }

    val action = when (phase) {
        MoonPhase.NEW_MOON -> "Set a clear, singular anchor intention and record initial hunches in your journal."
        MoonPhase.WAXING_CRESCENT -> "Execute the first visible action step indicated by your card before dusk."
        MoonPhase.FIRST_QUARTER -> "Identify one friction point or block and decisively confront it today."
        MoonPhase.WAXING_GIBBOUS -> "Fine-tune, refactor, and polish your current projects with focused stamina."
        MoonPhase.FULL_MOON -> "Celebrate completed milestones and acknowledge insights surfaced in your reading."
        MoonPhase.WANING_GIBBOUS -> "Document your lessons learned and share valuable insights with collaborators."
        MoonPhase.LAST_QUARTER -> "Release obsolete habits, redundant tasks, and unneeded attachments."
        MoonPhase.WANING_CRESCENT -> "Prioritize restorative rest, dream logging, and quiet contemplation."
    }

    return LunarTarotInfluence(
        synthesizedReadingGuidance = synthesized,
        tarotModulationNote = baseModulation,
        actionDirective = action
    )
}
