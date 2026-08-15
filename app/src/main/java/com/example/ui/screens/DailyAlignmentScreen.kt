package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.MoonPhase
import com.example.data.repository.DeckRepository
import com.example.data.util.LunarInfo
import com.example.ui.components.MoonPhaseVisualizer
import com.example.ui.components.ProminentLunarBiorhythmTracker
import com.example.ui.components.TarotCardView
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.ElementFireColor
import com.example.ui.theme.EncryptedGreen
import com.example.ui.theme.GoldGlow
import com.example.ui.theme.ObsidianBorder
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianDeep
import com.example.ui.theme.ObsidianSurface
import com.example.ui.theme.ObsidianSurfaceVariant
import com.example.ui.theme.StarlightGold
import com.example.ui.theme.StarlightMuted
import com.example.ui.theme.StarlightWhite
import com.example.ui.viewmodel.TarotViewModel

@Composable
fun DailyAlignmentScreen(
    viewModel: TarotViewModel,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formState by viewModel.dailyForm.collectAsState()
    val currentLunarInfo by viewModel.currentLunarInfo.collectAsState()

    var section1Expanded by remember { mutableStateOf(true) }
    var section2Expanded by remember { mutableStateOf(true) }
    var section3Expanded by remember { mutableStateOf(true) }
    var section4Expanded by remember { mutableStateOf(true) }
    var section5Expanded by remember { mutableStateOf(true) }
    var section6Expanded by remember { mutableStateOf(true) }

    val drawnCard = formState.drawnCards.firstOrNull()?.let {
        DeckRepository.getCardById(it.cardId)
    } ?: DeckRepository.allCards.first()
    val isReversed = formState.drawnCards.firstOrNull()?.isReversed == true

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDeep)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header with generated banner
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, ObsidianBorder, RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tarot_banner_art),
                    contentDescription = "Alchemical Tarot Altar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(Color.Transparent, ObsidianDeep.copy(alpha = 0.85f))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Text(
                        text = "DAILY TAROT ALIGNMENT",
                        color = StarlightGold,
                        fontSize = 18.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = "Daily Journal & Alchemical Laboratory Tracker",
                        color = StarlightMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // PROMINENT LIVE LUNAR PHASE & BIORHYTHM TRACKER
        item {
            ProminentLunarBiorhythmTracker(
                lunarInfo = currentLunarInfo,
                onSyncToDailyForm = {
                    viewModel.syncCalculatedLunarToDailyForm()
                    Toast.makeText(context, "✦ Live Lunar Alignment Synced to Journal Form!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // SECTION I: The Foundation
        item {
            TemplateSectionCard(
                sectionNumber = "I",
                title = "The Foundation",
                isExpanded = section1Expanded,
                onToggleExpand = { section1Expanded = !section1Expanded },
                icon = Icons.Default.LocationOn
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Date & Time Display
                    val dateFormatted = remember {
                        java.text.SimpleDateFormat("EEEE, MMM d, yyyy • h:mm a", java.util.Locale.getDefault()).format(java.util.Date())
                    }
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(0.5.dp, ObsidianBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "📅 Date & Time:",
                                color = StarlightGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = dateFormatted,
                                color = StarlightWhite,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Location Coordinates
                    JournalInputField(
                        label = "Location (Coordinates / City)",
                        value = formState.location,
                        onValueChange = { viewModel.updateDailyForm { copy(location = it) } },
                        placeholder = "e.g. 37.7749° N, 122.4194° W • San Francisco, CA"
                    )

                    // Moon Phase & Astro Transits
                    MoonPhaseVisualizer(
                        phase = formState.moonPhase,
                        astrologicalSign = formState.astrologicalTransits,
                        illuminationPercent = currentLunarInfo.illuminationPercentage
                    )

                    JournalInputField(
                        label = "Moon Phase & Astrological Transits",
                        value = formState.astrologicalTransits,
                        onValueChange = { viewModel.updateDailyForm { copy(astrologicalTransits = it) } },
                        placeholder = "e.g. Waning Crescent in Cancer • Moon sextile Mars"
                    )

                    // Vibe / Internal Weather
                    JournalInputField(
                        label = "Current \"Vibe\" / Internal Weather",
                        value = formState.vibe,
                        onValueChange = { viewModel.updateDailyForm { copy(vibe = it) } },
                        placeholder = "One word for your mood before drawing (e.g. Focused & Receptive)"
                    )
                }
            }
        }

        // SECTION II: The Draw
        item {
            TemplateSectionCard(
                sectionNumber = "II",
                title = "The Draw",
                isExpanded = section2Expanded,
                onToggleExpand = { section2Expanded = !section2Expanded },
                icon = Icons.Default.AutoAwesome
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    JournalInputField(
                        label = "The Question / Intention",
                        value = formState.question,
                        onValueChange = { viewModel.updateDailyForm { copy(question = it) } },
                        placeholder = "\"What energy should I embody today?\" or \"What is my focal point?\""
                    )

                    // Interactive Card Display
                    TarotCardView(
                        card = drawnCard,
                        isReversed = isReversed,
                        positionTitle = "Daily Focal Anchor",
                        cardWidth = 170.dp,
                        cardHeight = 265.dp,
                        onCardClick = {
                            viewModel.openCardDetail(drawnCard, isReversed)
                        },
                        onInfoClick = {
                            viewModel.openCardDetail(drawnCard, isReversed)
                        }
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.redrawDailyCard() },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = StarlightGold),
                            border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.6f)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Shuffle & Redraw", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = {
                                val current = formState.drawnCards.firstOrNull()
                                if (current != null) {
                                    val updated = current.copy(isReversed = !current.isReversed)
                                    viewModel.updateDailyForm { copy(drawnCards = listOf(updated)) }
                                }
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CelestialCyan),
                            border = BorderStroke(1.dp, CelestialCyan.copy(alpha = 0.6f)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(if (isReversed) "Set Upright" else "Set Reversed", fontSize = 12.sp)
                        }
                    }

                    // Visual Snapshot notes
                    JournalInputField(
                        label = "Visual Snapshot Notes",
                        value = formState.drawnCards.firstOrNull()?.snapshotNotes ?: "",
                        onValueChange = { text ->
                            val current = formState.drawnCards.firstOrNull() ?: return@JournalInputField
                            viewModel.updateDailyForm {
                                copy(drawnCards = listOf(current.copy(snapshotNotes = text)))
                            }
                        },
                        placeholder = "A sketch of the symbol or visual cue that stands out most..."
                    )
                }
            }
        }

        // SECTION III: Analysis & Synthesis
        item {
            TemplateSectionCard(
                sectionNumber = "III",
                title = "Analysis & Synthesis",
                isExpanded = section3Expanded,
                onToggleExpand = { section3Expanded = !section3Expanded },
                icon = Icons.Default.Psychology
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Technical Correspondences Summary
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, ObsidianBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "✦ TECHNICAL CORRESPONDENCES",
                                color = StarlightGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "• Element: ${drawnCard.element.symbol} ${drawnCard.element.label}\n• Numerology: ${drawnCard.numerologyMeaning}\n• Astrology: ${drawnCard.astrologyTransit}\n• Alchemical Stage: ${drawnCard.alchemicalCorrespondence}",
                                color = StarlightWhite,
                                fontSize = 12.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    // Intuitive First Hit
                    JournalInputField(
                        label = "Intuitive \"First Hit\"",
                        value = formState.intuitiveHit,
                        onValueChange = { viewModel.updateDailyForm { copy(intuitiveHit = it) } },
                        placeholder = "What was your immediate gut reaction to the imagery today?",
                        minLines = 2
                    )

                    // Traditional Meaning
                    JournalInputField(
                        label = "Traditional Meaning Reminder",
                        value = formState.traditionalMeaningNotes,
                        onValueChange = { viewModel.updateDailyForm { copy(traditionalMeaningNotes = it) } },
                        placeholder = "A brief 1-sentence reminder of book definition",
                        minLines = 2
                    )
                }
            }
        }

        // SECTION IV: Real-World Integration
        item {
            TemplateSectionCard(
                sectionNumber = "IV",
                title = "Real-World Integration",
                isExpanded = section4Expanded,
                onToggleExpand = { section4Expanded = !section4Expanded },
                icon = Icons.Default.Science
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    JournalInputField(
                        label = "Manifestation Hypothesis (Lab / Coding)",
                        value = formState.manifestationHypothesis,
                        onValueChange = { viewModel.updateDailyForm { copy(manifestationHypothesis = it) } },
                        placeholder = "How do you predict this card will show up in your laboratory work or programming projects today?",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Daily Action",
                        value = formState.dailyAction,
                        onValueChange = { viewModel.updateDailyForm { copy(dailyAction = it) } },
                        placeholder = "One small, concrete task you will do to honor this card's energy."
                    )
                }
            }
        }

        // SECTION V: Evening Reflection (The Review)
        item {
            TemplateSectionCard(
                sectionNumber = "V",
                title = "Evening Reflection (The Review)",
                isExpanded = section5Expanded,
                onToggleExpand = { section5Expanded = !section5Expanded },
                icon = Icons.Default.NightsStay
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    JournalInputField(
                        label = "Evening Reflection",
                        value = formState.eveningReflection,
                        onValueChange = { viewModel.updateDailyForm { copy(eveningReflection = it) } },
                        placeholder = "How did the day unfold and how did the card energy manifest?",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Synchronicity Log",
                        value = formState.synchronicityLog,
                        onValueChange = { viewModel.updateDailyForm { copy(synchronicityLog = it) } },
                        placeholder = "Did you see this card's symbols in the \"wild\" today?",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Accuracy Check",
                        value = formState.accuracyCheck,
                        onValueChange = { viewModel.updateDailyForm { copy(accuracyCheck = it) } },
                        placeholder = "How did the card's theme actually manifest compared to your morning hypothesis?",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Final Wisdom / Permanent Record",
                        value = formState.finalWisdom,
                        onValueChange = { viewModel.updateDailyForm { copy(finalWisdom = it) } },
                        placeholder = "What is the \"data point\" or lesson you’re taking away for your permanent record?",
                        minLines = 2
                    )
                }
            }
        }

        // SECTION VI: Lunar Cycle & Biorhythm Tracking
        item {
            TemplateSectionCard(
                sectionNumber = "VI",
                title = "Lunar Cycle & Biorhythm Tracking",
                isExpanded = section6Expanded,
                onToggleExpand = { section6Expanded = !section6Expanded },
                icon = Icons.Default.Tune
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // 1. Current Phase Summary
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.5.dp, ObsidianBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "✦ CURRENT PHASE SUMMARY",
                                color = StarlightGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = currentLunarInfo.phaseSymbol,
                                    fontSize = 28.sp
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "${currentLunarInfo.phaseName} (${currentLunarInfo.illuminationPercentage}% Illumination)",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = StarlightWhite
                                    )
                                    Text(
                                        text = "• Energy: ${if (currentLunarInfo.isWaxing) "Waxing (Building, Action & Expansion)" else "Waning (Release, Cleansing & Introspection)"}",
                                        fontSize = 11.sp,
                                        color = StarlightGold
                                    )
                                    Text(
                                        text = "• Astrological Sign: ${currentLunarInfo.zodiacSign} (${currentLunarInfo.zodiacElement})",
                                        fontSize = 11.sp,
                                        color = StarlightMuted
                                    )
                                }
                            }
                        }
                    }

                    // 2. Tarot-Lunar Correlation
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.5.dp, ObsidianBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "✦ TAROT-LUNAR CORRELATION",
                                color = StarlightGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            Text(
                                text = "Lunar Card Alignment: Pull one card to describe your relationship with the current phase's energy.",
                                fontSize = 11.sp,
                                color = StarlightWhite
                            )

                            val lunarCard = formState.lunarCardId?.let { DeckRepository.getCardById(it) }
                            if (lunarCard != null) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TarotCardView(
                                        card = lunarCard,
                                        isReversed = formState.lunarCardReversed,
                                        positionTitle = "Lunar Phase Alignment",
                                        cardWidth = 140.dp,
                                        cardHeight = 220.dp,
                                        onCardClick = { viewModel.openCardDetail(lunarCard, formState.lunarCardReversed) },
                                        onInfoClick = { viewModel.openCardDetail(lunarCard, formState.lunarCardReversed) }
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        OutlinedButton(
                                            onClick = { viewModel.drawLunarAlignmentCard() },
                                            shape = RoundedCornerShape(6.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = StarlightGold),
                                            border = BorderStroke(0.5.dp, StarlightGold)
                                        ) {
                                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(12.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("Repull Card", fontSize = 11.sp)
                                        }
                                        OutlinedButton(
                                            onClick = { viewModel.toggleLunarCardReversed() },
                                            shape = RoundedCornerShape(6.dp),
                                            colors = ButtonDefaults.outlinedButtonColors(contentColor = CelestialCyan),
                                            border = BorderStroke(0.5.dp, CelestialCyan)
                                        ) {
                                            Text(if (formState.lunarCardReversed) "Upright" else "Reversed", fontSize = 11.sp)
                                        }
                                    }
                                }
                            } else {
                                OutlinedButton(
                                    onClick = { viewModel.drawLunarAlignmentCard() },
                                    modifier = Modifier.fillMaxWidth().testTag("pull_lunar_card_button"),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StarlightGold),
                                    border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.7f)),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Icon(Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Pull Lunar Alignment Card", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            // Phase-Specific Action
                            JournalInputField(
                                label = "Phase-Specific Action Focus",
                                value = formState.phaseSpecificAction,
                                onValueChange = { viewModel.updateDailyForm { copy(phaseSpecificAction = it) } },
                                placeholder = "New Moon: Seeds/intentions • Full Moon: Realizations/harvest • Last Quarter: Releasing projects",
                                minLines = 2
                            )
                        }
                    }

                    // 3. Biorhythm & Energy Levels
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.5.dp, ObsidianBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "✦ BIORHYTHM & ENERGY LEVELS",
                                color = StarlightGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            // Emotional Wave 1-10 Slider
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Emotional Wave",
                                    color = StarlightWhite,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = "${"%.1f".format(formState.emotionalWave)} / 10.0",
                                    color = StarlightGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Rate your emotional energy and note fluctuation with visible illumination (${currentLunarInfo.illuminationPercentage}%).",
                                fontSize = 10.sp,
                                color = StarlightMuted
                            )
                            Slider(
                                value = formState.emotionalWave,
                                onValueChange = { viewModel.updateDailyForm { copy(emotionalWave = it) } },
                                valueRange = 1.0f..10.0f,
                                steps = 17,
                                colors = SliderDefaults.colors(
                                    thumbColor = StarlightGold,
                                    activeTrackColor = StarlightGold,
                                    inactiveTrackColor = ObsidianBorder
                                ),
                                modifier = Modifier.testTag("emotional_wave_slider")
                            )

                            // Laboratory Synchronization
                            JournalInputField(
                                label = "Laboratory Synchronization (Spagyric / Experiments)",
                                value = formState.laboratorySyncNotes,
                                onValueChange = { viewModel.updateDailyForm { copy(laboratorySyncNotes = it) } },
                                placeholder = "Did your spagyric experiments align with lunar timing? (e.g. Waxing creation aided synthesis, Waning aided purification)",
                                minLines = 2
                            )
                        }
                    }

                    // 4. Ritual & Manifestation Log
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(0.5.dp, ObsidianBorder),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "✦ RITUAL & MANIFESTATION LOG",
                                color = StarlightGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )

                            JournalInputField(
                                label = "Phase Ritual",
                                value = formState.phaseRitual,
                                onValueChange = { viewModel.updateDailyForm { copy(phaseRitual = it) } },
                                placeholder = "Specific ritual performed (e.g. candle for New Moon, releasing meditation for Full Moon)",
                                minLines = 2
                            )

                            JournalInputField(
                                label = "Lunar Synchronicities",
                                value = formState.lunarSynchronicities,
                                onValueChange = { viewModel.updateDailyForm { copy(lunarSynchronicities = it) } },
                                placeholder = "Did imagery from daily Tarot pulls match lunar symbols (e.g. The Moon, High Priestess)?",
                                minLines = 2
                            )
                        }
                    }

                    // Integration with Weekly Review Note
                    Surface(
                        color = GoldGlow.copy(alpha = 0.08f),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(0.5.dp, StarlightGold.copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = null,
                                tint = StarlightGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Integration with Weekly Review: Cross-reference your lunar phase data with technical milestones to uncover cycles in cognitive flow and spagyric intuition.",
                                fontSize = 10.sp,
                                color = StarlightMuted,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
            }
        }

        // Save & Encrypt Action Bar
        item {
            Button(
                onClick = {
                    viewModel.saveDailyJournalEntry {
                        Toast.makeText(context, "✦ Daily Tarot Alignment Encrypted & Logged!", Toast.LENGTH_SHORT).show()
                        onNavigateToHistory()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("save_daily_alignment_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = StarlightGold,
                    contentColor = ObsidianDeep
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save & Sync Encrypted Alignment",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun TemplateSectionCard(
    sectionNumber: String,
    title: String,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("template_section_$sectionNumber"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggleExpand() },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = ObsidianSurfaceVariant,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(32.dp),
                        border = BorderStroke(0.5.dp, StarlightGold.copy(alpha = 0.4f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = sectionNumber,
                                color = StarlightGold,
                                fontSize = 13.sp,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = title,
                        color = StarlightWhite,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                IconButton(onClick = onToggleExpand, modifier = Modifier.size(28.dp)) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Toggle Section",
                        tint = StarlightMuted
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    content()
                }
            }
        }
    }
}

@Composable
fun JournalInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        placeholder = { Text(placeholder, color = StarlightMuted.copy(alpha = 0.6f), fontSize = 12.sp) },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("input_${label.lowercase().replace(" ", "_")}"),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = StarlightWhite,
            unfocusedTextColor = StarlightWhite,
            focusedBorderColor = StarlightGold,
            unfocusedBorderColor = ObsidianBorder,
            focusedContainerColor = ObsidianSurface,
            unfocusedContainerColor = ObsidianSurface,
            focusedLabelColor = StarlightGold,
            unfocusedLabelColor = StarlightMuted
        ),
        shape = RoundedCornerShape(10.dp),
        minLines = minLines
    )
}
