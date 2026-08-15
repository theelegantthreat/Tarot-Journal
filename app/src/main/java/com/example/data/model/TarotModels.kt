package com.example.data.model

enum class Suit {
    MAJOR,
    WANDS,     // Fire: Action, Will, Energy, Spagyric Calcination
    CUPS,      // Water: Emotion, Intuition, Dreams, Dissolution
    SWORDS,    // Air: Mind, Logic, Code, Separation
    PENTACLES  // Earth: Manifestation, Laboratory, Physical, Coagulation
}

enum class Element(val label: String, val symbol: String, val colorHex: Long) {
    FIRE("Fire", "🜂", 0xFFE57373),
    WATER("Water", "🜄", 0xFF64B5F6),
    AIR("Air", "🜁", 0xFFFFD54F),
    EARTH("Earth", "🜃", 0xFF81C784),
    SPIRIT("Spirit", "🜀", 0xFFBA68C8)
}

enum class MoonPhase(val phaseName: String, val symbol: String, val generalQuality: String) {
    NEW_MOON("New Moon", "🌑", "Planting seeds & setting core intentions"),
    WAXING_CRESCENT("Waxing Crescent", "🌒", "Building momentum & initial action"),
    FIRST_QUARTER("First Quarter", "🌓", "Overcoming obstacles & decision making"),
    WAXING_GIBBOUS("Waxing Gibbous", "🌔", "Refining, perfecting & lab creation"),
    FULL_MOON("Full Moon", "🌕", "Peak illumination, realization & celebration"),
    WANING_GIBBOUS("Waning Gibbous", "🌖", "Sharing wisdom, gratitude & dissemination"),
    LAST_QUARTER("Last Quarter", "🌗", "Release, purification & letting go"),
    WANING_CRESCENT("Waning Crescent", "🌘", "Rest, recuperation & shadow surrender")
}

data class TarotCard(
    val id: Int,
    val name: String,
    val number: Int,
    val romanNumeral: String,
    val suit: Suit,
    val element: Element,
    val numerologyMeaning: String,
    val astrologyTransit: String,
    val uprightMeaning: String,
    val reversedMeaning: String,
    val keywords: List<String>,
    val alchemicalCorrespondence: String,
    val symbolDescription: String,
    val advice: String
)

data class DrawnCard(
    val cardId: Int,
    val isReversed: Boolean = false,
    val positionName: String = "Focal Point",
    val positionMeaning: String = "Current energy to embody",
    val intuitiveHit: String = "",
    val snapshotNotes: String = ""
)

enum class SpreadType(
    val title: String,
    val description: String,
    val cardCount: Int,
    val positions: List<Pair<String, String>>
) {
    DAILY_ALIGNMENT(
        title = "Daily Alignment Draw",
        description = "Single card focal anchor for intention, laboratory hypothesis & daily action",
        cardCount = 1,
        positions = listOf("Daily Anchor" to "What energy should I embody today?")
    ),
    THREE_CARD_TIMELINE(
        title = "Past / Present / Future",
        description = "Temporal progression of energy, project evolution, and upcoming breakthroughs",
        cardCount = 3,
        positions = listOf(
            "Past Roots" to "Foundational influences & past experiments",
            "Present State" to "Current focal reality & emotional wave",
            "Future Horizon" to "Emerging outcome & project trajectory"
        )
    ),
    THREE_CARD_MIND_BODY_SPIRIT(
        title = "Mind / Body / Spirit",
        description = "Holistic calibration across mental focus, laboratory stamina, and intuitive connection",
        cardCount = 3,
        positions = listOf(
            "Mind (Air)" to "Mental clarity, coding architecture & strategy",
            "Body (Earth)" to "Physical stamina, laboratory timing & execution",
            "Spirit (Fire/Water)" to "Intuitive resonance & higher purpose"
        )
    ),
    FIVE_CARD_CROSS(
        title = "5-Card Alchemical Cross",
        description = "Deep diagnosis of current bottlenecks, subconscious drivers, and catalytic advice",
        cardCount = 5,
        positions = listOf(
            "The Core" to "Central theme & active vibe",
            "The Challenge" to "Friction, shadow block or technical obstacle",
            "The Root" to "Subconscious basis & alchemical trigger",
            "The Crown" to "Highest potential manifestation",
            "The Resolution" to "Actionable synthesis & integration"
        )
    ),
    WEEKLY_SYNTHESIS_SPREAD(
        title = "Weekly Synthesis Anchor",
        description = "Comprehensive anchor for weekly retrospectives, project milestones and shadow work",
        cardCount = 4,
        positions = listOf(
            "Weekly Anchor" to "Overarching theme guiding this cycle",
            "Technical Catalyst" to "App development & lab milestone focus",
            "Shadow & Dream Mirror" to "Subconscious triggers & dream deciphering",
            "Wisdom Outcome" to "Data point & permanent lesson"
        )
    ),
    CELTIC_CROSS(
        title = "Celtic Cross (10 Cards)",
        description = "Master spread for profound life, career, and spiritual architecture mapping",
        cardCount = 10,
        positions = listOf(
            "Present" to "Heart of the situation",
            "Challenge" to "Immediate opposing forces",
            "Subconscious" to "Underlying basis & dreams",
            "Past" to "Passing influences",
            "Crown" to "Aspirations & best outcome",
            "Near Future" to "Approaching conditions",
            "Self Attitude" to "Internal stance & vibe",
            "Environment" to "External energies & collaborators",
            "Hopes & Fears" to "Deepest longings and anxieties",
            "Final Outcome" to "Long-range synthesis"
        )
    )
}
