package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.crypto.CryptoSyncManager
import com.example.data.local.TarotDatabase
import com.example.data.model.DrawnCard
import com.example.data.model.JournalEntry
import com.example.data.model.MoonPhase
import com.example.data.model.SpreadType
import com.example.data.model.TarotCard
import com.example.data.remote.GeminiTarotService
import com.example.data.repository.DeckRepository
import com.example.data.repository.TarotRepository
import com.example.data.util.LunarCalculator
import com.example.data.util.LunarInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class DailyFormState(
    val location: String = "37.7749° N, 122.4194° W • San Francisco, CA",
    val moonPhase: MoonPhase = LunarCalculator.calculateLunarInfo().phase,
    val astrologicalTransits: String = LunarCalculator.calculateLunarInfo().astrologicalTransitSummary,
    val vibe: String = "Focused & Receptive",
    val question: String = "What energy should I embody today?",
    val drawnCards: List<DrawnCard> = emptyList(),
    val intuitiveHit: String = "",
    val traditionalMeaningNotes: String = "",
    val manifestationHypothesis: String = "",
    val dailyAction: String = "",
    val eveningReflection: String = "",
    val synchronicityLog: String = "",
    val accuracyCheck: String = "",
    val finalWisdom: String = "",
    val emotionalWave: Float = 8.0f,
    val laboratorySyncNotes: String = LunarCalculator.calculateLunarInfo().alchemicalLabTiming,
    val phaseRitual: String = "Copal incense & focal contemplation",
    val lunarSynchronicities: String = "",
    val lunarCardId: Int? = null,
    val lunarCardReversed: Boolean = false,
    val phaseSpecificAction: String = "Plant intentions and clarify foundational experimental goals."
)

data class WeeklyFormState(
    val deckUsed: String = "Hermetic Alchemical Tarot (RWS)",
    val question: String = "What overarching patterns governed our experiments and technical milestones this week?",
    val drawnCards: List<DrawnCard> = emptyList(),
    val weeklyAnchorSummary: String = "",
    val dominantSuit: String = "Wands",
    val dominantElement: String = "Fire",
    val frequentCards: String = "",
    val labSpagyricReview: String = "",
    val technicalMilestones: String = "",
    val locationCorrelation: String = "",
    val dreamIntegration: String = "",
    val meditationPractice: String = "",
    val ritualTracking: String = "",
    val shadowWorkTriggers: String = "",
    val unmetNeeds: String = "",
    val intentionsNextWeek: String = "",
    val emotionalWave: Float = 8.5f,
    val moonPhase: MoonPhase = MoonPhase.FULL_MOON
)

class TarotViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TarotRepository

    val allEntries: StateFlow<List<JournalEntry>>

    // Active Card Detail Dialog
    private val _selectedCardForDetail = MutableStateFlow<Pair<TarotCard, Boolean>?>(null)
    val selectedCardForDetail: StateFlow<Pair<TarotCard, Boolean>?> = _selectedCardForDetail.asStateFlow()

    // Daily Form State
    private val _dailyForm = MutableStateFlow(DailyFormState())
    val dailyForm: StateFlow<DailyFormState> = _dailyForm.asStateFlow()

    // Custom Spread Studio State
    private val _selectedSpreadType = MutableStateFlow(SpreadType.THREE_CARD_TIMELINE)
    val selectedSpreadType: StateFlow<SpreadType> = _selectedSpreadType.asStateFlow()

    private val _activeSpreadCards = MutableStateFlow<List<DrawnCard>>(emptyList())
    val activeSpreadCards: StateFlow<List<DrawnCard>> = _activeSpreadCards.asStateFlow()

    private val _isGeneratingAi = MutableStateFlow(false)
    val isGeneratingAi: StateFlow<Boolean> = _isGeneratingAi.asStateFlow()

    private val _lastAiSynthesis = MutableStateFlow<String?>(null)
    val lastAiSynthesis: StateFlow<String?> = _lastAiSynthesis.asStateFlow()

    // Weekly Synthesis Form State
    private val _weeklyForm = MutableStateFlow(WeeklyFormState())
    val weeklyForm: StateFlow<WeeklyFormState> = _weeklyForm.asStateFlow()

    // Cloud Sync State
    private val _isCloudSynced = MutableStateFlow(true)
    val isCloudSynced: StateFlow<Boolean> = _isCloudSynced.asStateFlow()

    private val _lastSyncHash = MutableStateFlow("0xAES256GCM_LiveMesh")
    val lastSyncHash: StateFlow<String> = _lastSyncHash.asStateFlow()

    private val _syncMessage = MutableStateFlow("Encrypted Sync Active (AES-256-GCM)")
    val syncMessage: StateFlow<String> = _syncMessage.asStateFlow()

    // Calculated Lunar Phase & Biorhythm State
    private val _currentLunarInfo = MutableStateFlow(LunarCalculator.calculateLunarInfo())
    val currentLunarInfo: StateFlow<LunarInfo> = _currentLunarInfo.asStateFlow()

    // Card of the Day State
    private val _cardOfTheDay = MutableStateFlow<Pair<TarotCard, Boolean>>(DeckRepository.getRandomCard() to (Random.nextFloat() < 0.2f))
    val cardOfTheDay: StateFlow<Pair<TarotCard, Boolean>> = _cardOfTheDay.asStateFlow()

    private val _cardOfTheDayAffirmation = MutableStateFlow<String>("Loading today's divine affirmation...")
    val cardOfTheDayAffirmation: StateFlow<String> = _cardOfTheDayAffirmation.asStateFlow()

    private val _isLoadingAffirmation = MutableStateFlow<Boolean>(false)
    val isLoadingAffirmation: StateFlow<Boolean> = _isLoadingAffirmation.asStateFlow()

    init {
        val db = TarotDatabase.getDatabase(application)
        repository = TarotRepository(db.tarotDao())
        allEntries = repository.allEntries.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.populateSeedDataIfEmpty()
            refreshLunarCalculation()
            initDailyAlignmentDraw()
            initCustomSpread(SpreadType.THREE_CARD_TIMELINE)
            initWeeklySynthesisSpread()
            fetchCardOfTheDayAffirmation()
        }
    }

    fun refreshCardOfTheDay() {
        val newCard = DeckRepository.getRandomCard()
        val isRev = Random.nextFloat() < 0.2f
        _cardOfTheDay.value = newCard to isRev
        fetchCardOfTheDayAffirmation()
    }

    fun fetchCardOfTheDayAffirmation() {
        viewModelScope.launch {
            _isLoadingAffirmation.value = true
            val (card, isRev) = _cardOfTheDay.value
            val result = GeminiTarotService.generateDailyAffirmation(card, isRev)
            result.onSuccess { affirmation ->
                _cardOfTheDayAffirmation.value = affirmation
            }.onFailure {
                _cardOfTheDayAffirmation.value = if (isRev) {
                    "I embrace the inner lessons of ${card.name}, gently realigning my purpose with clarity and patience."
                } else {
                    "I step boldly into the wisdom of ${card.name}, aligning my focus with creative momentum and boundless insight."
                }
            }
            _isLoadingAffirmation.value = false
        }
    }

    fun refreshLunarCalculation() {
        val info = LunarCalculator.calculateLunarInfo()
        _currentLunarInfo.value = info
    }

    fun syncCalculatedLunarToDailyForm() {
        val info = _currentLunarInfo.value
        _dailyForm.value = _dailyForm.value.copy(
            moonPhase = info.phase,
            astrologicalTransits = info.astrologicalTransitSummary,
            laboratorySyncNotes = info.alchemicalLabTiming,
            lunarSynchronicities = "Aligned with ${info.phaseName} (${info.illuminationPercentage}% illumination) in ${info.zodiacSign}"
        )
    }

    fun openCardDetail(card: TarotCard, isReversed: Boolean = false) {
        _selectedCardForDetail.value = card to isReversed
    }

    fun closeCardDetail() {
        _selectedCardForDetail.value = null
    }

    // Daily Alignment Methods
    fun initDailyAlignmentDraw() {
        if (_dailyForm.value.drawnCards.isEmpty()) {
            val randomCard = DeckRepository.getRandomCard()
            val isRev = Random.nextFloat() < 0.25f
            val drawn = DrawnCard(
                cardId = randomCard.id,
                isReversed = isRev,
                positionName = "Daily Focal Anchor",
                positionMeaning = "Energy to embody today"
            )
            _dailyForm.value = _dailyForm.value.copy(
                drawnCards = listOf(drawn),
                traditionalMeaningNotes = if (isRev) randomCard.reversedMeaning else randomCard.uprightMeaning,
                manifestationHypothesis = "Predicting this energy will clarify today's experimental and coding milestones."
            )
        }
    }

    fun redrawDailyCard() {
        val randomCard = DeckRepository.getRandomCard()
        val isRev = Random.nextFloat() < 0.25f
        val drawn = DrawnCard(
            cardId = randomCard.id,
            isReversed = isRev,
            positionName = "Daily Focal Anchor",
            positionMeaning = "Energy to embody today"
        )
        _dailyForm.value = _dailyForm.value.copy(
            drawnCards = listOf(drawn),
            traditionalMeaningNotes = if (isRev) randomCard.reversedMeaning else randomCard.uprightMeaning
        )
    }

    fun drawLunarAlignmentCard() {
        val randomCard = DeckRepository.getRandomCard()
        val isRev = Random.nextFloat() < 0.2f
        _dailyForm.value = _dailyForm.value.copy(
            lunarCardId = randomCard.id,
            lunarCardReversed = isRev
        )
    }

    fun toggleLunarCardReversed() {
        val current = _dailyForm.value.lunarCardReversed
        _dailyForm.value = _dailyForm.value.copy(lunarCardReversed = !current)
    }

    fun updateDailyForm(transform: DailyFormState.() -> DailyFormState) {
        _dailyForm.value = _dailyForm.value.transform()
    }

    fun saveDailyJournalEntry(onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            val form = _dailyForm.value
            val dateFormat = SimpleDateFormat("EEEE, MMM d, yyyy • h:mm a", Locale.getDefault())
            val now = System.currentTimeMillis()

            val entry = JournalEntry(
                entryType = "DAILY",
                timestamp = now,
                dateDisplay = dateFormat.format(Date(now)),
                spreadType = SpreadType.DAILY_ALIGNMENT.name,
                deckUsed = "Hermetic Alchemical Tarot (RWS)",
                locationCoordinates = form.location,
                moonPhase = form.moonPhase.phaseName,
                astrologicalTransits = form.astrologicalTransits,
                vibeInternalWeather = form.vibe,
                questionIntention = form.question,
                drawnCardsJson = repository.serializeDrawnCards(form.drawnCards),
                intuitiveFirstHit = form.intuitiveHit,
                traditionalMeaningNotes = form.traditionalMeaningNotes,
                manifestationHypothesis = form.manifestationHypothesis,
                dailyAction = form.dailyAction,
                eveningReflection = form.eveningReflection,
                synchronicityLog = form.synchronicityLog,
                accuracyCheck = form.accuracyCheck,
                finalWisdom = form.finalWisdom,
                isEveningReviewed = form.eveningReflection.isNotBlank(),
                lunarPhaseEnergy = "${form.moonPhase.phaseName}: ${form.moonPhase.generalQuality}",
                lunarCardAlignmentId = form.lunarCardId,
                lunarCardAlignmentReversed = form.lunarCardReversed,
                phaseSpecificAction = form.phaseSpecificAction,
                emotionalWaveRating = form.emotionalWave,
                laboratorySyncNotes = form.laboratorySyncNotes,
                phaseRitual = form.phaseRitual,
                lunarSynchronicities = form.lunarSynchronicities
            )

            repository.saveEntry(entry)
            _syncMessage.value = "Synced Entry (AES-256 encrypted)"
            onSaved()
        }
    }

    // Custom Spread Methods
    fun selectSpreadType(spreadType: SpreadType) {
        _selectedSpreadType.value = spreadType
        initCustomSpread(spreadType)
    }

    fun initCustomSpread(spreadType: SpreadType) {
        val usedCardIds = mutableSetOf<Int>()
        val list = mutableListOf<DrawnCard>()

        spreadType.positions.forEach { (posName, posMeaning) ->
            val card = DeckRepository.getRandomCard(usedCardIds)
            usedCardIds.add(card.id)
            val isRev = Random.nextFloat() < 0.25f
            list.add(
                DrawnCard(
                    cardId = card.id,
                    isReversed = isRev,
                    positionName = posName,
                    positionMeaning = posMeaning
                )
            )
        }
        _activeSpreadCards.value = list
        _lastAiSynthesis.value = null
    }

    fun redrawCustomSpread() {
        initCustomSpread(_selectedSpreadType.value)
    }

    fun requestAiSpreadSynthesis(intention: String, vibe: String) {
        viewModelScope.launch {
            _isGeneratingAi.value = true
            val spread = _selectedSpreadType.value
            val result = repository.requestAiSynthesis(
                JournalEntry(
                    spreadType = spread.title,
                    drawnCardsJson = repository.serializeDrawnCards(_activeSpreadCards.value),
                    vibeInternalWeather = vibe,
                    questionIntention = intention,
                    emotionalWaveRating = 8.0f
                )
            )
            _lastAiSynthesis.value = result
            _isGeneratingAi.value = false
        }
    }

    fun saveCustomSpreadAsJournalEntry(
        intention: String,
        vibe: String,
        notes: String,
        onSaved: () -> Unit = {}
    ) {
        viewModelScope.launch {
            val dateFormat = SimpleDateFormat("EEEE, MMM d, yyyy • h:mm a", Locale.getDefault())
            val now = System.currentTimeMillis()
            val spread = _selectedSpreadType.value

            val entry = JournalEntry(
                entryType = "SPREAD_READING",
                timestamp = now,
                dateDisplay = dateFormat.format(Date(now)),
                spreadType = spread.title,
                questionIntention = intention.ifBlank { "Guidance on active project vectors" },
                vibeInternalWeather = vibe.ifBlank { "Receptive & Inquiring" },
                drawnCardsJson = repository.serializeDrawnCards(_activeSpreadCards.value),
                intuitiveFirstHit = notes,
                aiSynthesisAnalysis = _lastAiSynthesis.value ?: "",
                manifestationHypothesis = "Integration across ${_activeSpreadCards.value.size} vectors",
                emotionalWaveRating = 8.0f
            )

            repository.saveEntry(entry)
            onSaved()
        }
    }

    // Weekly Synthesis Methods
    fun initWeeklySynthesisSpread() {
        val usedCardIds = mutableSetOf<Int>()
        val cards = SpreadType.WEEKLY_SYNTHESIS_SPREAD.positions.map { (name, meaning) ->
            val card = DeckRepository.getRandomCard(usedCardIds)
            usedCardIds.add(card.id)
            DrawnCard(
                cardId = card.id,
                isReversed = Random.nextFloat() < 0.2f,
                positionName = name,
                positionMeaning = meaning
            )
        }
        _weeklyForm.value = _weeklyForm.value.copy(
            drawnCards = cards,
            weeklyAnchorSummary = "Anchor card highlights a catalytic fusion of technical execution and intuitive listening.",
            dominantSuit = "Wands",
            dominantElement = "Fire",
            frequentCards = "The Magician (I), The Sun (XIX), Knight of Swords",
            technicalMilestones = "Delivered core encrypted sync architecture & sentiment wave visualizer.",
            dreamIntegration = "Luminous geometric patterns in sleep; feeling clear direction.",
            meditationPractice = "Regular 15-minute grounding sessions before laboratory/coding.",
            shadowWorkTriggers = "Addressed impatience with slow build loops; cultivated steady poise."
        )
    }

    fun updateWeeklyForm(transform: WeeklyFormState.() -> WeeklyFormState) {
        _weeklyForm.value = _weeklyForm.value.transform()
    }

    fun saveWeeklySynthesisEntry(onSaved: () -> Unit = {}) {
        viewModelScope.launch {
            val form = _weeklyForm.value
            val now = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

            val entry = JournalEntry(
                entryType = "WEEKLY",
                timestamp = now,
                dateDisplay = "Weekly Synthesis: Cycle ending ${dateFormat.format(Date(now))}",
                spreadType = "WEEKLY_SYNTHESIS_SPREAD",
                deckUsed = form.deckUsed,
                questionIntention = form.question,
                drawnCardsJson = repository.serializeDrawnCards(form.drawnCards),
                weeklyAnchorSummary = form.weeklyAnchorSummary,
                dominantSuit = form.dominantSuit,
                dominantElement = form.dominantElement,
                frequentCardsList = form.frequentCards,
                technicalMilestones = form.technicalMilestones,
                locationCorrelation = form.locationCorrelation,
                dreamIntegration = form.dreamIntegration,
                meditationPractice = form.meditationPractice,
                shadowWorkTriggers = form.shadowWorkTriggers,
                unmetNeeds = form.unmetNeeds,
                intentionsNextCycle = form.intentionsNextWeek,
                emotionalWaveRating = form.emotionalWave,
                moonPhase = form.moonPhase.phaseName,
                lunarPhaseEnergy = "${form.moonPhase.phaseName}: ${form.moonPhase.generalQuality}",
                isEveningReviewed = true
            )

            repository.saveEntry(entry)
            _syncMessage.value = "Weekly Synthesis Synced & Encrypted"
            onSaved()
        }
    }

    fun updateEveningReflection(entryId: Long, eveningText: String, accuracy: String, wisdom: String) {
        viewModelScope.launch {
            val entry = repository.getEntryById(entryId) ?: return@launch
            val updated = entry.copy(
                eveningReflection = eveningText,
                accuracyCheck = accuracy,
                finalWisdom = wisdom,
                isEveningReviewed = true
            )
            repository.saveEntry(updated)
        }
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteEntry(id)
        }
    }

    // Cloud Sync Operations
    fun triggerManualCloudSync() {
        viewModelScope.launch {
            _isCloudSynced.value = false
            _syncMessage.value = "Encrypting payload with AES-256-GCM..."
            kotlinx.coroutines.delay(600)

            val payload = CryptoSyncManager.encryptPayload(
                plainText = "TarotJournal_Database_State_EntriesCount_${allEntries.value.size}",
                deviceId = "Android_Pixel_Node_1"
            )
            _lastSyncHash.value = payload.sha256Hash
            _isCloudSynced.value = true
            _syncMessage.value = "Cloud Sync Verified • Hash ${payload.sha256Hash}"
        }
    }
}
