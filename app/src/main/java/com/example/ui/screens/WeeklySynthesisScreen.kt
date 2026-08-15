package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SpreadType
import com.example.data.repository.DeckRepository
import com.example.ui.components.TarotCardView
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.ElementFireColor
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
fun WeeklySynthesisScreen(
    viewModel: TarotViewModel,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val formState by viewModel.weeklyForm.collectAsState()

    var section1Expanded by remember { mutableStateOf(true) }
    var section2Expanded by remember { mutableStateOf(true) }
    var section3Expanded by remember { mutableStateOf(true) }
    var section4Expanded by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDeep)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "WEEKLY TAROT SYNTHESIS",
                color = StarlightGold,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Overarching patterns, lab milestone alignment, and subconscious integration",
                color = StarlightMuted,
                fontSize = 12.sp
            )
        }

        // 4-Card Quadrant Visualizer
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                border = BorderStroke(1.dp, ObsidianBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✧ WEEKLY 4-CARD SYNTHESIS SPREAD ✧",
                        color = StarlightGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        formState.drawnCards.forEach { drawn ->
                            val card = DeckRepository.getCardById(drawn.cardId)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(125.dp)
                            ) {
                                TarotCardView(
                                    card = card,
                                    isReversed = drawn.isReversed,
                                    positionTitle = drawn.positionName,
                                    cardWidth = 125.dp,
                                    cardHeight = 190.dp,
                                    onCardClick = {
                                        viewModel.openCardDetail(card, drawn.isReversed)
                                    },
                                    onInfoClick = {
                                        viewModel.openCardDetail(card, drawn.isReversed)
                                    }
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = drawn.positionMeaning,
                                    color = StarlightMuted,
                                    fontSize = 9.sp,
                                    lineHeight = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // SECTION I: Arcana Synthesis & Patterns
        item {
            TemplateSectionCard(
                sectionNumber = "I",
                title = "Arcana Synthesis & Patterns",
                isExpanded = section1Expanded,
                onToggleExpand = { section1Expanded = !section1Expanded },
                icon = Icons.Default.AutoAwesome
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    JournalInputField(
                        label = "Weekly Anchor Card Summary",
                        value = formState.weeklyAnchorSummary,
                        onValueChange = { viewModel.updateWeeklyForm { copy(weeklyAnchorSummary = it) } },
                        placeholder = "Which card served as the focal anchor guiding this cycle?",
                        minLines = 2
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = formState.dominantSuit,
                            onValueChange = { viewModel.updateWeeklyForm { copy(dominantSuit = it) } },
                            label = { Text("Dominant Suit", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
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
                            shape = RoundedCornerShape(10.dp)
                        )

                        OutlinedTextField(
                            value = formState.dominantElement,
                            onValueChange = { viewModel.updateWeeklyForm { copy(dominantElement = it) } },
                            label = { Text("Dominant Element", fontSize = 11.sp) },
                            modifier = Modifier.weight(1f),
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
                            shape = RoundedCornerShape(10.dp)
                        )
                    }

                    JournalInputField(
                        label = "Recurring / Frequent Cards",
                        value = formState.frequentCards,
                        onValueChange = { viewModel.updateWeeklyForm { copy(frequentCards = it) } },
                        placeholder = "Cards that appeared multiple times across daily draws..."
                    )
                }
            }
        }

        // SECTION II: Real-World & Technical Integration
        item {
            TemplateSectionCard(
                sectionNumber = "II",
                title = "Real-World & Technical Integration",
                isExpanded = section2Expanded,
                onToggleExpand = { section2Expanded = !section2Expanded },
                icon = Icons.Default.Science
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    JournalInputField(
                        label = "App Development & Milestones",
                        value = formState.technicalMilestones,
                        onValueChange = { viewModel.updateWeeklyForm { copy(technicalMilestones = it) } },
                        placeholder = "What major technical features, refactors, or benchmarks were achieved?",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Location & Workspace Correlation",
                        value = formState.locationCorrelation,
                        onValueChange = { viewModel.updateWeeklyForm { copy(locationCorrelation = it) } },
                        placeholder = "Did insights or productivity correlate with specific physical lab coordinates?"
                    )
                }
            }
        }

        // SECTION III: Subconscious, Shadow & Dream Work
        item {
            TemplateSectionCard(
                sectionNumber = "III",
                title = "Subconscious, Shadow & Dream Work",
                isExpanded = section3Expanded,
                onToggleExpand = { section3Expanded = !section3Expanded },
                icon = Icons.Default.NightsStay
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    JournalInputField(
                        label = "Dream Deciphering & Integration",
                        value = formState.dreamIntegration,
                        onValueChange = { viewModel.updateWeeklyForm { copy(dreamIntegration = it) } },
                        placeholder = "Notable symbols, archetypes, or emotional currents in dreams...",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Shadow Work & Friction Triggers",
                        value = formState.shadowWorkTriggers,
                        onValueChange = { viewModel.updateWeeklyForm { copy(shadowWorkTriggers = it) } },
                        placeholder = "Emotional triggers, blockages, or cognitive frictions observed...",
                        minLines = 2
                    )

                    JournalInputField(
                        label = "Meditation & Stillness Practice",
                        value = formState.meditationPractice,
                        onValueChange = { viewModel.updateWeeklyForm { copy(meditationPractice = it) } },
                        placeholder = "Grounding rituals, breathwork, and stillness sessions logged..."
                    )
                }
            }
        }

        // SECTION IV: Intentions & Emotional Wave
        item {
            TemplateSectionCard(
                sectionNumber = "IV",
                title = "Next Cycle Intentions & Biorhythm",
                isExpanded = section4Expanded,
                onToggleExpand = { section4Expanded = !section4Expanded },
                icon = Icons.Default.ShowChart
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Weekly Emotional Energy Average: ${"%.1f".format(formState.emotionalWave)} / 10.0",
                        color = StarlightGold,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Slider(
                        value = formState.emotionalWave,
                        onValueChange = { viewModel.updateWeeklyForm { copy(emotionalWave = it) } },
                        valueRange = 1.0f..10.0f,
                        steps = 17,
                        colors = SliderDefaults.colors(
                            thumbColor = StarlightGold,
                            activeTrackColor = StarlightGold,
                            inactiveTrackColor = ObsidianBorder
                        )
                    )

                    JournalInputField(
                        label = "Intentions for the Next Cycle",
                        value = formState.intentionsNextWeek,
                        onValueChange = { viewModel.updateWeeklyForm { copy(intentionsNextWeek = it) } },
                        placeholder = "Key focuses and commitments for the coming week...",
                        minLines = 2
                    )
                }
            }
        }

        // Save & Encrypt Button
        item {
            Button(
                onClick = {
                    viewModel.saveWeeklySynthesisEntry {
                        Toast.makeText(context, "✦ Weekly Synthesis Encrypted & Logged!", Toast.LENGTH_SHORT).show()
                        onNavigateToHistory()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("save_weekly_synthesis_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = StarlightGold,
                    contentColor = ObsidianDeep
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save & Log Weekly Synthesis",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
