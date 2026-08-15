package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.FilterVintage
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SpreadType
import com.example.data.repository.DeckRepository
import com.example.ui.components.SpreadVisualizer
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
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
fun SpreadStudioScreen(
    viewModel: TarotViewModel,
    onNavigateToHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedSpread by viewModel.selectedSpreadType.collectAsState()
    val activeCards by viewModel.activeSpreadCards.collectAsState()
    val isGeneratingAi by viewModel.isGeneratingAi.collectAsState()
    val aiSynthesis by viewModel.lastAiSynthesis.collectAsState()

    var intentionText by remember { mutableStateOf("") }
    var vibeText by remember { mutableStateOf("") }
    var notesText by remember { mutableStateOf("") }

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
                text = "CUSTOM SPREAD STUDIO",
                color = StarlightGold,
                fontSize = 18.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Select sacred geometry spreads, shuffle the deck, and analyze positions",
                color = StarlightMuted,
                fontSize = 12.sp
            )
        }

        // Spread Type Selector Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SpreadType.values().forEach { spread ->
                    val isSelected = spread == selectedSpread
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectSpreadType(spread) },
                        label = {
                            Text(
                                text = "${spread.title} (${spread.cardCount})",
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StarlightGold,
                            selectedLabelColor = ObsidianDeep,
                            containerColor = ObsidianSurfaceVariant,
                            labelColor = StarlightWhite
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            borderColor = if (isSelected) StarlightGold else ObsidianBorder,
                            enabled = true,
                            selected = isSelected
                        ),
                        modifier = Modifier.testTag("spread_chip_${spread.name.lowercase()}")
                    )
                }
            }
        }

        // Spread Visualizer Panel
        item {
            SpreadVisualizer(
                spreadType = selectedSpread,
                drawnCards = activeCards,
                onCardClick = { drawn ->
                    val card = DeckRepository.getCardById(drawn.cardId)
                    viewModel.openCardDetail(card, drawn.isReversed)
                }
            )
        }

        // Action Controls (Shuffle & Redraw)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = { viewModel.redrawCustomSpread() },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StarlightGold),
                    border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.7f)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("shuffle_spread_button")
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Shuffle & Redraw Spread", fontSize = 12.sp)
                }
            }
        }

        // Intention & Context Inputs
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                border = BorderStroke(1.dp, ObsidianBorder)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "✧ SPREAD INTENTION & INQUIRY",
                        color = StarlightGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    OutlinedTextField(
                        value = intentionText,
                        onValueChange = { intentionText = it },
                        label = { Text("Core Question / Inquiring Focus", fontSize = 12.sp) },
                        placeholder = { Text("What vectors require illumination?", fontSize = 12.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_spread_intention"),
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
                        value = vibeText,
                        onValueChange = { vibeText = it },
                        label = { Text("Internal Vibe & Energy", fontSize = 12.sp) },
                        placeholder = { Text("e.g. Seeking high-voltage focus for coding", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
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
                        value = notesText,
                        onValueChange = { notesText = it },
                        label = { Text("Interpretation & First Impressions", fontSize = 12.sp) },
                        placeholder = { Text("Your intuitive hits on how these cards interact...", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
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
                        minLines = 2
                    )
                }
            }
        }

        // Automated AI & Alchemical Synthesis
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = ObsidianCard),
                border = BorderStroke(1.dp, Brush.linearGradient(listOf(AmethystPurple, ObsidianBorder, CelestialCyan)))
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
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
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Automated AI Tarot & Sentiment Synthesis",
                                color = StarlightWhite,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    if (aiSynthesis != null) {
                        Surface(
                            color = ObsidianSurfaceVariant,
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.3f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = aiSynthesis ?: "",
                                color = StarlightWhite,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.requestAiSpreadSynthesis(intentionText, vibeText)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmethystPurple,
                            contentColor = ObsidianDeep
                        ),
                        shape = RoundedCornerShape(10.dp),
                        enabled = !isGeneratingAi,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generate_ai_synthesis_button")
                    ) {
                        if (isGeneratingAi) {
                            CircularProgressIndicator(
                                color = ObsidianDeep,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Consulting Alchemical Synthesis...", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(Icons.Default.Psychology, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                if (aiSynthesis == null) "Synthesize Spread with AI / Wisdom Engine" else "Regenerate Synthesis",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Save & Log Spread Button
        item {
            Button(
                onClick = {
                    viewModel.saveCustomSpreadAsJournalEntry(
                        intention = intentionText,
                        vibe = vibeText,
                        notes = notesText,
                        onSaved = {
                            Toast.makeText(context, "✦ Spread Logged to Encrypted Database!", Toast.LENGTH_SHORT).show()
                            onNavigateToHistory()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("save_spread_button"),
                colors = ButtonDefaults.buttonColors(
                    containerColor = StarlightGold,
                    contentColor = ObsidianDeep
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Save & Log Spread to Cloud Journal",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
