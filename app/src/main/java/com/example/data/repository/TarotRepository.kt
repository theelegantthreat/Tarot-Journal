package com.example.data.repository

import com.example.data.analysis.SentimentAnalyzer
import com.example.data.crypto.CryptoSyncManager
import com.example.data.local.TarotDao
import com.example.data.model.DrawnCard
import com.example.data.model.JournalEntry
import com.example.data.remote.GeminiTarotService
import kotlinx.coroutines.flow.Flow
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TarotRepository(private val tarotDao: TarotDao) {

    val allEntries: Flow<List<JournalEntry>> = tarotDao.getAllEntries()

    suspend fun getEntryById(id: Long): JournalEntry? = tarotDao.getEntryById(id)

    suspend fun saveEntry(entry: JournalEntry): Long {
        // Run automated sentiment analysis on save
        val sentiment = SentimentAnalyzer.analyze(
            vibe = entry.vibeInternalWeather,
            intuitiveHit = entry.intuitiveFirstHit,
            manifestationHypothesis = entry.manifestationHypothesis,
            eveningReflection = entry.eveningReflection,
            shadowWork = entry.shadowWorkTriggers,
            emotionalWaveRating = entry.emotionalWaveRating
        )

        // Encrypt payload hash
        val payloadHash = CryptoSyncManager.calculateSha256(
            "${entry.timestamp}_${entry.questionIntention}_${entry.drawnCardsJson}"
        ).take(10)

        val updatedEntry = entry.copy(
            sentimentScore = sentiment.score,
            sentimentLabel = sentiment.label,
            sentimentIntensity = sentiment.intensity,
            detectedThemes = sentiment.detectedThemes.joinToString(", "),
            encryptionHash = "0x$payloadHash",
            isSyncedToCloud = true,
            lastSyncedTimestamp = System.currentTimeMillis()
        )

        return if (updatedEntry.id == 0L) {
            tarotDao.insertEntry(updatedEntry)
        } else {
            tarotDao.updateEntry(updatedEntry)
            updatedEntry.id
        }
    }

    suspend fun deleteEntry(id: Long) {
        tarotDao.deleteEntryById(id)
    }

    suspend fun requestAiSynthesis(entry: JournalEntry): String {
        val drawnList = parseDrawnCards(entry.drawnCardsJson)
        val result = GeminiTarotService.generateTarotSynthesis(
            entryType = entry.entryType,
            spreadName = entry.spreadType,
            drawnCards = drawnList,
            vibe = entry.vibeInternalWeather,
            question = entry.questionIntention,
            hypothesis = entry.manifestationHypothesis,
            moonPhase = entry.moonPhase,
            emotionalRating = entry.emotionalWaveRating
        )
        return result.getOrDefault("")
    }

    suspend fun populateSeedDataIfEmpty() {
        val dateFormat = SimpleDateFormat("EEEE, MMM d, yyyy • h:mm a", Locale.getDefault())
        val now = System.currentTimeMillis()
        val oneDay = 24 * 60 * 60 * 1000L

        // Daily reading 1: The Magician
        val dailyCards1 = listOf(
            DrawnCard(
                cardId = 1, // The Magician
                isReversed = false,
                positionName = "Daily Focal Anchor",
                positionMeaning = "What energy should I embody today?",
                intuitiveHit = "Strong golden current, felt lightning in the fingertips upon drawing. All four elements are active on the altar.",
                snapshotNotes = "Focused on the infinity lemniscate above the Magician's head."
            )
        )

        val entry1 = JournalEntry(
            entryType = "DAILY",
            timestamp = now - (oneDay * 2),
            dateDisplay = dateFormat.format(Date(now - (oneDay * 2))),
            spreadType = "DAILY_ALIGNMENT",
            deckUsed = "Hermetic Alchemical Tarot (RWS)",
            locationCoordinates = "37.7749° N, 122.4194° W • San Francisco, CA",
            moonPhase = "Waxing Crescent in Cancer",
            astrologicalTransits = "Moon in Cancer (Water) • Sun in Leo (Fire)",
            vibeInternalWeather = "Electric, Focused & Galvanized",
            questionIntention = "What energy should I embody in my programming architecture today?",
            drawnCardsJson = serializeDrawnCards(dailyCards1),
            intuitiveFirstHit = "All tools are laid out. Time to stop second-guessing and execute the core compiler loop.",
            traditionalMeaningNotes = "Manifestation through focused willpower and mastery of the four elements.",
            manifestationHypothesis = "I predict this card will manifest as rapid resolution of the recursive parser bug in our OmniPage module.",
            dailyAction = "Refactor the AST token parser without checking phone notifications for 2 hours straight.",
            eveningReflection = "The recursive parser was resolved in 45 minutes cleanly. Found myself in effortless flow state.",
            synchronicityLog = "Saw an infinity symbol painted on a storefront while walking to get coffee at 11:11 AM.",
            accuracyCheck = "Morning hypothesis proved 100% accurate; mental clarity was at peak capacity.",
            finalWisdom = "Preparation eliminates friction. When the blueprint is clear, the code flows effortlessly.",
            isEveningReviewed = true,
            lunarPhaseEnergy = "Waxing Crescent: Building momentum & initial action",
            lunarCardAlignmentId = 17, // The Star
            lunarCardAlignmentReversed = false,
            phaseSpecificAction = "Drafted seed specifications for the cloud sync encryption engine.",
            emotionalWaveRating = 8.8f,
            laboratorySyncNotes = "Spagyric extraction of rosemary completed during waxing hour with clear distillation.",
            phaseRitual = "Lit frankincense and set intention stone on desk.",
            lunarSynchronicities = "The Star card mirrored the clear night sky visible through lab window.",
            sentimentScore = 0.82f,
            sentimentLabel = "High Alchemical Radiance & Flow",
            sentimentIntensity = 0.88f,
            detectedThemes = "Manifestation, Flow, Execution, Clarity",
            isSyncedToCloud = true,
            encryptionHash = "0x8f2a1b94c3",
            lastSyncedTimestamp = now - (oneDay * 2)
        )

        // Daily reading 2: High Priestess & Moon phase
        val dailyCards2 = listOf(
            DrawnCard(
                cardId = 2, // High Priestess
                isReversed = false,
                positionName = "Daily Focal Anchor",
                positionMeaning = "What energy should I embody today?",
                intuitiveHit = "Deep stillness. The pillars of black and white Boaz/Jachin urge emotional equilibrium.",
                snapshotNotes = "The pomegranate veil and crescent moon at her feet."
            )
        )

        val entry2 = JournalEntry(
            entryType = "DAILY",
            timestamp = now - oneDay,
            dateDisplay = dateFormat.format(Date(now - oneDay)),
            spreadType = "DAILY_ALIGNMENT",
            deckUsed = "Hermetic Alchemical Tarot (RWS)",
            locationCoordinates = "37.7749° N, 122.4194° W • San Francisco, CA",
            moonPhase = "Waxing Gibbous in Pisces",
            astrologicalTransits = "Moon in Pisces (Water) • Mercury sextile Neptune",
            vibeInternalWeather = "Intuitive, Dreamy & Contemplative",
            questionIntention = "How can I align subconscious intuition with my experimental data?",
            drawnCardsJson = serializeDrawnCards(dailyCards2),
            intuitiveFirstHit = "Trust the subtle pattern recognition rather than forcing blunt metrics.",
            traditionalMeaningNotes = "Subconscious wisdom, esoteric knowing, mystery, intuitive receptivity.",
            manifestationHypothesis = "An unexpected insight will reveal why the database indexing was locking up under concurrent load.",
            dailyAction = "Sit in 15 minutes of silent meditation before touching any codebase.",
            eveningReflection = "While resting, realized the room database DAO query needed a Flow dispatcher shift.",
            synchronicityLog = "Noticed a blue veil curtain blowing in the wind exactly matching the card imagery.",
            accuracyCheck = "Intuitive hit directly provided the technical fix.",
            finalWisdom = "The subconscious solves algorithms while the active intellect rests.",
            isEveningReviewed = true,
            lunarPhaseEnergy = "Waxing Gibbous: Refining, perfecting & lab creation",
            lunarCardAlignmentId = 18, // The Moon
            lunarCardAlignmentReversed = false,
            phaseSpecificAction = "Polished UI transitions and refined color contrast values.",
            emotionalWaveRating = 7.4f,
            laboratorySyncNotes = "Waxing Gibbous supported crystallization phase in lab mineral purification.",
            phaseRitual = "Copal and lavender smoke meditation.",
            lunarSynchronicities = "Water themes repeating across all notes and ambient sounds.",
            sentimentScore = 0.68f,
            sentimentLabel = "Grounded Clarity & Harmonious Focus",
            sentimentIntensity = 0.72f,
            detectedThemes = "Intuition, Subconscious, Serenity",
            isSyncedToCloud = true,
            encryptionHash = "0x91d4e73f2a",
            lastSyncedTimestamp = now - oneDay
        )

        // Weekly Synthesis Entry
        val weeklyCards = listOf(
            DrawnCard(cardId = 14, isReversed = false, positionName = "Weekly Anchor", positionMeaning = "Overarching theme guiding this cycle"),
            DrawnCard(cardId = 8, isReversed = false, positionName = "Technical Catalyst", positionMeaning = "App development & lab milestone focus"),
            DrawnCard(cardId = 15, isReversed = true, positionName = "Shadow & Dream Mirror", positionMeaning = "Subconscious triggers & dream deciphering"),
            DrawnCard(cardId = 21, isReversed = false, positionName = "Wisdom Outcome", positionMeaning = "Data point & permanent lesson")
        )

        val entry3 = JournalEntry(
            entryType = "WEEKLY",
            timestamp = now - (oneDay * 7),
            dateDisplay = "Weekly Synthesis: Cycle of Leo-Pisces Lunar Window",
            spreadType = "WEEKLY_SYNTHESIS_SPREAD",
            deckUsed = "Hermetic Alchemical Tarot (RWS)",
            locationCoordinates = "37.7749° N, 122.4194° W • San Francisco, CA",
            moonPhase = "Full Moon in Aquarius",
            astrologicalTransits = "Full Moon Peak Illumination • Sun in Leo",
            vibeInternalWeather = "Synthesis, Mastery & Transcendence",
            questionIntention = "What overarching patterns governed our experiments and technical milestones this week?",
            drawnCardsJson = serializeDrawnCards(weeklyCards),
            weeklyAnchorSummary = "Temperance (XIV) served as our anchor card, reminding us that blending opposing disciplines (spagyric laboratory timing and Android software architecture) creates the philosopher's stone.",
            dominantSuit = "Wands",
            dominantElement = "Fire",
            frequentCardsList = "The Magician (I), Temperance (XIV), Strength (VIII)",
            technicalMilestones = "Completed AES-256 GCM encrypted sync architecture, integrated Room reactive DAO flows, and calibrated automated sentiment tracking.",
            locationCorrelation = "Strongest mental breakthroughs occurred at laboratory bench coordinates.",
            dreamIntegration = "Subconscious dreams featured vast libraries of luminous codices and rivers flowing uphill (reversed Devil = liberation from cognitive friction).",
            meditationPractice = "Conducted five 20-minute silent meditations prior to coding sprints.",
            shadowWorkTriggers = "Confronted perfectionist attachment to rewriting working modules; released need for artificial control.",
            unmetNeeds = "Required more outdoor grounding time to balance intense screen hours.",
            intentionsNextCycle = "1. Maintain 8+ emotional wave rating. 2. Implement lunar correlation charts. 3. Document spagyric alchemical stages.",
            lunarPhaseEnergy = "Full Moon: Celebrating harvests & major realizations",
            lunarCardAlignmentId = 19, // The Sun
            lunarCardAlignmentReversed = false,
            phaseSpecificAction = "Conducted complete weekly code review and alchemical synthesis report.",
            emotionalWaveRating = 9.0f,
            laboratorySyncNotes = "Full Moon culmination aligned with separation of essential oils.",
            phaseRitual = "Full moon silver bowl reflection ritual and cedar burning.",
            lunarSynchronicities = "Double solar and lunar imagery appeared in testing telemetry.",
            sentimentScore = 0.91f,
            sentimentLabel = "High Alchemical Radiance & Flow",
            sentimentIntensity = 0.95f,
            detectedThemes = "Synthesis, Mastery, Liberation, Harmony",
            isSyncedToCloud = true,
            encryptionHash = "0xaa417bc820",
            lastSyncedTimestamp = now - (oneDay * 7)
        )

        tarotDao.insertEntry(entry1)
        tarotDao.insertEntry(entry2)
        tarotDao.insertEntry(entry3)
    }

    fun parseDrawnCards(json: String): List<DrawnCard> {
        if (json.isBlank() || json == "[]") return emptyList()
        val list = mutableListOf<DrawnCard>()
        try {
            val array = JSONArray(json)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    DrawnCard(
                        cardId = obj.getInt("cardId"),
                        isReversed = obj.optBoolean("isReversed", false),
                        positionName = obj.optString("positionName", "Focal Point"),
                        positionMeaning = obj.optString("positionMeaning", ""),
                        intuitiveHit = obj.optString("intuitiveHit", ""),
                        snapshotNotes = obj.optString("snapshotNotes", "")
                    )
                )
            }
        } catch (e: Exception) {
            // fallback
        }
        return list
    }

    fun serializeDrawnCards(cards: List<DrawnCard>): String {
        val array = JSONArray()
        for (c in cards) {
            val obj = JSONObject().apply {
                put("cardId", c.cardId)
                put("isReversed", c.isReversed)
                put("positionName", c.positionName)
                put("positionMeaning", c.positionMeaning)
                put("intuitiveHit", c.intuitiveHit)
                put("snapshotNotes", c.snapshotNotes)
            }
            array.put(obj)
        }
        return array.toString()
    }
}
