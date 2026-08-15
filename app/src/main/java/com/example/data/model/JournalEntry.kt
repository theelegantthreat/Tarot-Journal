package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tarot_journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    // Metadata
    val entryType: String = "DAILY", // "DAILY", "WEEKLY", "CUSTOM"
    val timestamp: Long = System.currentTimeMillis(),
    val dateDisplay: String = "",
    val spreadType: String = "DAILY_ALIGNMENT",
    val deckUsed: String = "Hermetic Alchemical Tarot (RWS)",

    // Section I: The Foundation
    val locationCoordinates: String = "37.7749° N, 122.4194° W • San Francisco, CA",
    val moonPhase: String = "Waxing Gibbous",
    val astrologicalTransits: String = "Moon in Pisces • Sun in Leo",
    val vibeInternalWeather: String = "Focused & Receptive",

    // Section II: The Draw
    val questionIntention: String = "What energy should I embody today?",
    val drawnCardsJson: String = "[]", // Serialized List<DrawnCard>

    // Section III: Analysis & Synthesis
    val intuitiveFirstHit: String = "",
    val traditionalMeaningNotes: String = "",

    // Section IV: Real-World Integration
    val manifestationHypothesis: String = "", // e.g. prediction for lab/app code
    val dailyAction: String = "",

    // Section V: Evening Reflection
    val eveningReflection: String = "",
    val synchronicityLog: String = "",
    val accuracyCheck: String = "",
    val finalWisdom: String = "",
    val isEveningReviewed: Boolean = false,

    // Section VI: Lunar Cycle & Biorhythm Tracking
    val lunarPhaseEnergy: String = "Waxing for building, creation & expansion",
    val lunarCardAlignmentId: Int? = null,
    val lunarCardAlignmentReversed: Boolean = false,
    val phaseSpecificAction: String = "",
    val emotionalWaveRating: Float = 7.5f, // 1.0 - 10.0
    val laboratorySyncNotes: String = "", // e.g. Spagyric / creation experiments
    val phaseRitual: String = "Copal incense & focal contemplation",
    val lunarSynchronicities: String = "",

    // Weekly Synthesis Specifics (Section III & IV of Weekly Template)
    val weeklyAnchorSummary: String = "",
    val dominantSuit: String = "Wands",
    val dominantElement: String = "Fire",
    val frequentCardsList: String = "",
    val technicalMilestones: String = "", // App development progress / roadblocks
    val locationCorrelation: String = "",
    val dreamIntegration: String = "",
    val meditationPractice: String = "",
    val shadowWorkTriggers: String = "",
    val unmetNeeds: String = "",
    val intentionsNextCycle: String = "",

    // Automated Sentiment & Emotional Tracking
    val sentimentScore: Float = 0.65f, // -1.0 to +1.0
    val sentimentLabel: String = "High Focus & Alchemical Alignment",
    val sentimentIntensity: Float = 0.8f, // 0.0 to 1.0
    val detectedThemes: String = "Manifestation, Deep Focus, Intuition",
    val aiSynthesisAnalysis: String = "",

    // Encrypted Cloud Sync System
    val isSyncedToCloud: Boolean = true,
    val encryptionHash: String = "0xAES256GCM_8f94c1a2",
    val lastSyncedTimestamp: Long = System.currentTimeMillis(),
    val deviceOriginId: String = "Pixel_Pro_Android15"
)
