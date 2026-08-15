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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.crypto.CryptoSyncManager
import com.example.data.model.JournalEntry
import com.example.data.repository.DeckRepository
import com.example.data.repository.TarotRepository
import com.example.ui.components.SentimentWaveChart
import com.example.ui.components.TarotCardView
import com.example.ui.theme.AmethystPurple
import com.example.ui.theme.CelestialCyan
import com.example.ui.theme.EncryptedGreen
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
fun HistoryLogScreen(
    viewModel: TarotViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val entries by viewModel.allEntries.collectAsState()
    val isSynced by viewModel.isCloudSynced.collectAsState()
    val syncHash by viewModel.lastSyncHash.collectAsState()
    val syncMsg by viewModel.syncMessage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("ALL") } // ALL, DAILY, SPREAD_READING, WEEKLY

    var activeDetailEntry by remember { mutableStateOf<JournalEntry?>(null) }
    var activeEveningReviewEntry by remember { mutableStateOf<JournalEntry?>(null) }

    val filteredEntries = entries.filter { entry ->
        val matchesFilter = when (selectedFilter) {
            "DAILY" -> entry.entryType == "DAILY"
            "SPREAD" -> entry.entryType == "SPREAD_READING"
            "WEEKLY" -> entry.entryType == "WEEKLY"
            else -> true
        }
        val matchesSearch = searchQuery.isBlank() ||
                entry.questionIntention.contains(searchQuery, ignoreCase = true) ||
                entry.vibeInternalWeather.contains(searchQuery, ignoreCase = true) ||
                entry.intuitiveFirstHit.contains(searchQuery, ignoreCase = true) ||
                entry.spreadType.contains(searchQuery, ignoreCase = true)

        matchesFilter && matchesSearch
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianDeep)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // Encrypted Cloud Sync Status Bar
            CloudSyncStatusBar(
                isSynced = isSynced,
                syncHash = syncHash,
                statusMessage = syncMsg,
                onSyncClick = { viewModel.triggerManualCloudSync() }
            )
        }

        // Emotional Wave & Automated Sentiment Visualizer
        item {
            SentimentWaveChart(entries = entries)
        }

        // Search Bar & Filter Chips
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search readings, intentions, symbols...", fontSize = 12.sp, color = StarlightMuted) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = StarlightGold) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_history_input"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = StarlightWhite,
                    unfocusedTextColor = StarlightWhite,
                    focusedBorderColor = StarlightGold,
                    unfocusedBorderColor = ObsidianBorder,
                    focusedContainerColor = ObsidianSurface,
                    unfocusedContainerColor = ObsidianSurface
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "ALL" to "All Logs (${entries.size})",
                    "DAILY" to "Daily Alignments",
                    "SPREAD" to "Custom Spreads",
                    "WEEKLY" to "Weekly Syntheses"
                ).forEach { (key, label) ->
                    val isSelected = selectedFilter == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedFilter = key },
                        label = { Text(label, fontSize = 11.sp) },
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
                        )
                    )
                }
            }
        }

        // List of Journal Entries
        items(filteredEntries, key = { it.id }) { entry ->
            JournalEntryCard(
                entry = entry,
                onCardClick = { activeDetailEntry = entry },
                onCardViewRequested = { card, isRev -> viewModel.openCardDetail(card, isRev) },
                onEveningReviewClick = { activeEveningReviewEntry = entry },
                onExportEncrypted = {
                    val payload = CryptoSyncManager.encryptPayload(
                        "${entry.dateDisplay}\n${entry.questionIntention}\n${entry.drawnCardsJson}\n${entry.intuitiveFirstHit}"
                    )
                    clipboardManager.setText(AnnotatedString(payload.cipherTextBase64))
                    Toast.makeText(context, "Encrypted Payload (AES-GCM) copied to clipboard!", Toast.LENGTH_SHORT).show()
                },
                onDeleteClick = { viewModel.deleteEntry(entry.id) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Modal: Full Historical Interpretation Notes Dialog
    activeDetailEntry?.let { entry ->
        HistoricalDetailModal(
            entry = entry,
            onDismiss = { activeDetailEntry = null },
            onCardInspect = { card, isRev -> viewModel.openCardDetail(card, isRev) }
        )
    }

    // Modal: Evening Reflection Review Dialog
    activeEveningReviewEntry?.let { entry ->
        EveningReflectionDialog(
            entry = entry,
            onDismiss = { activeEveningReviewEntry = null },
            onSave = { eveningText, accuracy, wisdom ->
                viewModel.updateEveningReflection(entry.id, eveningText, accuracy, wisdom)
                activeEveningReviewEntry = null
                Toast.makeText(context, "Evening Reflection Updated & Synced!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
private fun CloudSyncStatusBar(
    isSynced: Boolean,
    syncHash: String,
    statusMessage: String,
    onSyncClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cloud_sync_status_bar"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianSurfaceVariant),
        border = BorderStroke(1.dp, EncryptedGreen.copy(alpha = 0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Encrypted Sync",
                    tint = EncryptedGreen,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = statusMessage,
                        color = StarlightWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Encrypted Mesh Sync (AES-256-GCM) • Hash: $syncHash",
                        color = StarlightMuted,
                        fontSize = 9.sp
                    )
                }
            }

            IconButton(onClick = onSyncClick, modifier = Modifier.size(28.dp)) {
                Icon(
                    imageVector = Icons.Default.CloudSync,
                    contentDescription = "Sync Cloud Now",
                    tint = StarlightGold,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun JournalEntryCard(
    entry: JournalEntry,
    onCardClick: () -> Unit,
    onCardViewRequested: (com.example.data.model.TarotCard, Boolean) -> Unit,
    onEveningReviewClick: () -> Unit,
    onExportEncrypted: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val drawnCards = try {
        org.json.JSONArray(entry.drawnCardsJson).let { arr ->
            (0 until arr.length()).map { i ->
                val obj = arr.getJSONObject(i)
                com.example.data.model.DrawnCard(
                    cardId = obj.getInt("cardId"),
                    isReversed = obj.optBoolean("isReversed", false),
                    positionName = obj.optString("positionName", "Focal Point"),
                    positionMeaning = obj.optString("positionMeaning", "")
                )
            }
        }
    } catch (e: Exception) {
        emptyList()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
            .testTag("journal_entry_${entry.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ObsidianCard),
        border = BorderStroke(1.dp, ObsidianBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top Meta: Date + Moon Phase + Sentiment Tag
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = entry.dateDisplay,
                        color = StarlightGold,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${entry.spreadType.replace("_", " ")} • ${entry.moonPhase}",
                        color = StarlightMuted,
                        fontSize = 10.sp
                    )
                }

                Surface(
                    color = AmethystPurple.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp),
                    border = BorderStroke(0.5.dp, AmethystPurple)
                ) {
                    Text(
                        text = entry.sentimentLabel.take(24),
                        color = AmethystPurple,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Intention Question
            Text(
                text = "“${entry.questionIntention}”",
                color = StarlightWhite,
                fontSize = 14.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Horizontal Visual Spread Cards Preview
            if (drawnCards.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    drawnCards.forEach { drawn ->
                        val card = DeckRepository.getCardById(drawn.cardId)
                        TarotCardView(
                            card = card,
                            isReversed = drawn.isReversed,
                            positionTitle = drawn.positionName,
                            cardWidth = 90.dp,
                            cardHeight = 140.dp,
                            onCardClick = { onCardViewRequested(card, drawn.isReversed) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Intuitive Hit / Hypothesis summary
            if (entry.intuitiveFirstHit.isNotBlank()) {
                Text(
                    text = entry.intuitiveFirstHit,
                    color = StarlightMuted,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Bottom Actions & Evening Review Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (entry.entryType == "DAILY") {
                    if (entry.isEveningReviewed) {
                        Surface(
                            color = EncryptedGreen.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "✓ Evening Review Complete",
                                color = EncryptedGreen,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    } else {
                        OutlinedButton(
                            onClick = onEveningReviewClick,
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, StarlightGold.copy(alpha = 0.6f)),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = StarlightGold),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            modifier = Modifier.height(28.dp)
                        ) {
                            Icon(Icons.Default.NightsStay, contentDescription = null, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Log Evening Review", fontSize = 10.sp)
                        }
                    }
                } else {
                    Text(
                        text = "Wave: ${"%.1f".format(entry.emotionalWaveRating)}/10",
                        color = CelestialCyan,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onExportEncrypted, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Share, contentDescription = "Export Encrypted Payload", tint = StarlightMuted, modifier = Modifier.size(16.dp))
                    }
                    IconButton(onClick = onDeleteClick, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete Reading", tint = StarlightMuted, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun HistoricalDetailModal(
    entry: JournalEntry,
    onDismiss: () -> Unit,
    onCardInspect: (com.example.data.model.TarotCard, Boolean) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = StarlightGold, contentColor = ObsidianDeep),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Close Record", fontWeight = FontWeight.Bold)
            }
        },
        containerColor = ObsidianSurface,
        title = {
            Column {
                Text(
                    text = entry.dateDisplay,
                    color = StarlightGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${entry.spreadType} • ${entry.moonPhase}",
                    color = StarlightMuted,
                    fontSize = 11.sp
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = "“${entry.questionIntention}”",
                        color = StarlightWhite,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }

                if (entry.vibeInternalWeather.isNotBlank()) {
                    item {
                        DetailSection("Internal Vibe / Weather", entry.vibeInternalWeather, CelestialCyan)
                    }
                }

                if (entry.intuitiveFirstHit.isNotBlank()) {
                    item {
                        DetailSection("Intuitive First Hit", entry.intuitiveFirstHit, StarlightWhite)
                    }
                }

                if (entry.traditionalMeaningNotes.isNotBlank()) {
                    item {
                        DetailSection("Traditional Correspondences", entry.traditionalMeaningNotes, StarlightMuted)
                    }
                }

                if (entry.manifestationHypothesis.isNotBlank()) {
                    item {
                        DetailSection("Manifestation Hypothesis (Lab / Code)", entry.manifestationHypothesis, StarlightGold)
                    }
                }

                if (entry.dailyAction.isNotBlank()) {
                    item {
                        DetailSection("Daily Action", entry.dailyAction, StarlightWhite)
                    }
                }

                if (entry.aiSynthesisAnalysis.isNotBlank()) {
                    item {
                        DetailSection("AI & Alchemical Synthesis", entry.aiSynthesisAnalysis, AmethystPurple)
                    }
                }

                if (entry.eveningReflection.isNotBlank()) {
                    item {
                        DetailSection("Evening Reflection & Accuracy", "${entry.eveningReflection}\n\nAccuracy: ${entry.accuracyCheck}\nWisdom: ${entry.finalWisdom}", EncryptedGreen)
                    }
                }

                if (entry.technicalMilestones.isNotBlank()) {
                    item {
                        DetailSection("Technical Milestones Achieved", entry.technicalMilestones, StarlightWhite)
                    }
                }

                if (entry.shadowWorkTriggers.isNotBlank()) {
                    item {
                        DetailSection("Shadow Work & Dream Deciphering", "${entry.shadowWorkTriggers}\n\nDreams: ${entry.dreamIntegration}", Color(0xFFFF8A80))
                    }
                }

                item {
                    DetailSection(
                        "Cryptographic Signature",
                        "AES-256-GCM Secure Cloud Mesh • Hash: ${entry.encryptionHash} • Synced: ${entry.isSyncedToCloud}",
                        EncryptedGreen
                    )
                }
            }
        }
    )
}

@Composable
private fun DetailSection(title: String, content: String, titleColor: Color) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(ObsidianCard)
            .padding(10.dp)
    ) {
        Text(
            text = title,
            color = titleColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = content,
            color = StarlightWhite,
            fontSize = 12.sp,
            lineHeight = 17.sp
        )
    }
}

@Composable
private fun EveningReflectionDialog(
    entry: JournalEntry,
    onDismiss: () -> Unit,
    onSave: (String, String, String) -> Unit
) {
    var eveningReflection by remember { mutableStateOf<String>(entry.eveningReflection) }
    var accuracyCheck by remember { mutableStateOf<String>(entry.accuracyCheck) }
    var finalWisdom by remember { mutableStateOf<String>(entry.finalWisdom) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ObsidianSurface,
        title = {
            Text(
                text = "Log Evening Review (Section V)",
                color = StarlightGold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Morning Intention: “${entry.questionIntention}”",
                    color = StarlightMuted,
                    fontSize = 11.sp
                )

                OutlinedTextField(
                    value = eveningReflection,
                    onValueChange = { eveningReflection = it },
                    label = { Text("Evening Reflection & Synchronicities", fontSize = 11.sp) },
                    placeholder = { Text("What unfolded today?", fontSize = 11.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = StarlightWhite,
                        unfocusedTextColor = StarlightWhite,
                        focusedBorderColor = StarlightGold,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedContainerColor = ObsidianCard,
                        unfocusedContainerColor = ObsidianCard
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2
                )

                OutlinedTextField(
                    value = accuracyCheck,
                    onValueChange = { accuracyCheck = it },
                    label = { Text("Accuracy Check", fontSize = 11.sp) },
                    placeholder = { Text("How did it manifest vs hypothesis?", fontSize = 11.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = StarlightWhite,
                        unfocusedTextColor = StarlightWhite,
                        focusedBorderColor = StarlightGold,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedContainerColor = ObsidianCard,
                        unfocusedContainerColor = ObsidianCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = finalWisdom,
                    onValueChange = { finalWisdom = it },
                    label = { Text("Final Wisdom (Data Point)", fontSize = 11.sp) },
                    placeholder = { Text("Permanent takeaway for your log...", fontSize = 11.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = StarlightWhite,
                        unfocusedTextColor = StarlightWhite,
                        focusedBorderColor = StarlightGold,
                        unfocusedBorderColor = ObsidianBorder,
                        focusedContainerColor = ObsidianCard,
                        unfocusedContainerColor = ObsidianCard
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(eveningReflection, accuracyCheck, finalWisdom) },
                colors = ButtonDefaults.buttonColors(containerColor = StarlightGold, contentColor = ObsidianDeep)
            ) {
                Text("Save Evening Review", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = StarlightMuted)
            }
        }
    )
}
