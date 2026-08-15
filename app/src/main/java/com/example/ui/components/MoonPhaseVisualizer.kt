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
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
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
import com.example.data.model.MoonPhase
import com.example.data.util.LunarCalculator
import com.example.data.util.LunarInfo
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.DarkPurple
import com.example.ui.theme.DeepAmethyst
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardSecondary
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightSubtle
import com.example.ui.theme.StarlightWhite

@Composable
fun ProminentLunarBiorhythmTracker(
    lunarInfo: LunarInfo,
    onSyncToDailyForm: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var expandedDetails by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("prominent_lunar_biorhythm_tracker"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Section
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.NightsStay,
                        contentDescription = "Lunar Biorhythm",
                        tint = StarlightGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "ASTRONOMICAL LUNAR PHASE",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = StarlightGold,
                        letterSpacing = 1.2.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    color = ObsidianSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(0.5.dp, StarlightGold.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(StarlightGold)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = "LIVE ASTRONOMY",
                            color = StarlightWhite,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Phase Showcase
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Orb Display
                Box(
                    modifier = Modifier
                        .size(68.dp)
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
                        fontSize = 36.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lunarInfo.phaseName,
                        color = StarlightWhite,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Surface(
                        color = GoldGlow.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(0.5.dp, StarlightGold.copy(alpha = 0.5f))
                    ) {
                        Text(
                            text = "${lunarInfo.illuminationPercentage}% Illumination",
                            color = StarlightGold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = lunarInfo.astrologicalTransitSummary,
                        color = StarlightGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "Day ${lunarInfo.moonAgeDays} of 29.5-day synodic cycle • Next ${lunarInfo.nextMajorPhaseName} in ${lunarInfo.daysUntilNextMajorPhase}d",
                        color = StarlightMuted,
                        fontSize = 10.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Illumination & Cycle Progress Gauge
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Synodic Cycle Position",
                        fontSize = 10.sp,
                        color = StarlightSubtle
                    )
                    Text(
                        text = "${(lunarInfo.cycleProgress * 100).toInt()}% • ${if (lunarInfo.isWaxing) "Waxing ↗" else "Waning ↘"}",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = StarlightWhite
                    )
                }

                LinearProgressIndicator(
                    progress = { lunarInfo.cycleProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = StarlightGold,
                    trackColor = ObsidianSurfaceVariant,
                    strokeCap = StrokeCap.Round
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Biorhythm Guidance Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = ObsidianCardSecondary,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(0.5.dp, ObsidianBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = StarlightGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Biorhythm Energy Focus",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = StarlightGold
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = lunarInfo.biorhythmEnergyFocus,
                        fontSize = 11.sp,
                        color = StarlightWhite,
                        fontWeight = FontWeight.Medium
                    )

                    AnimatedVisibility(visible = expandedDetails) {
                        Column(modifier = Modifier.padding(top = 8.dp)) {
                            HorizontalDivider(
                                color = ObsidianBorder,
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Science,
                                    contentDescription = null,
                                    tint = CelestialCyan,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = "Spagyric & Laboratory Timing",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CelestialCyan
                                )
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = lunarInfo.alchemicalLabTiming,
                                fontSize = 10.sp,
                                color = StarlightMuted,
                                lineHeight = 14.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Emotional Resonance: ${lunarInfo.emotionalResonance}",
                                fontSize = 10.sp,
                                color = StarlightSubtle,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Action Buttons (Expand & Sync)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { expandedDetails = !expandedDetails }
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (expandedDetails) "Less Details" else "Laboratory Insights",
                        color = StarlightMuted,
                        fontSize = 11.sp
                    )
                    Icon(
                        imageVector = if (expandedDetails) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = StarlightMuted,
                        modifier = Modifier.size(14.dp)
                    )
                }

                OutlinedButton(
                    onClick = onSyncToDailyForm,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StarlightGold),
                    border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(32.dp).testTag("sync_lunar_phase_button")
                ) {
                    Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(13.dp))
                    Spacer(modifier = Modifier.width(5.dp))
                    Text("Apply Alignment", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun MoonPhaseVisualizer(
    phase: MoonPhase = MoonPhase.WAXING_GIBBOUS,
    astrologicalSign: String = "Moon in Pisces (Water)",
    illuminationPercent: Int = 84,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("moon_phase_visualizer"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Glowing Moon Orb
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(
                                GoldGlow.copy(alpha = 0.9f),
                                AmethystPurple.copy(alpha = 0.4f),
                                Color(0xFF1E1038)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = phase.symbol,
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Center Info
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = phase.phaseName,
                        color = StarlightGold,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "$illuminationPercent% Illum",
                            color = CelestialCyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = astrologicalSign,
                    color = StarlightWhite,
                    fontSize = 11.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = phase.generalQuality,
                    color = StarlightMuted,
                    fontSize = 10.sp,
                    maxLines = 1
                )
            }
        }
    }
}

