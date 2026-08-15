package com.example.data.util

import com.example.data.model.MoonPhase
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

data class LunarInfo(
    val phase: MoonPhase,
    val phaseName: String,
    val phaseSymbol: String,
    val illuminationPercentage: Int,
    val moonAgeDays: Double,
    val cycleProgress: Float, // 0.0 to 1.0
    val isWaxing: Boolean,
    val zodiacSign: String,
    val zodiacElement: String,
    val astrologicalTransitSummary: String,
    val biorhythmEnergyFocus: String,
    val alchemicalLabTiming: String,
    val nextMajorPhaseName: String,
    val daysUntilNextMajorPhase: Int,
    val emotionalResonance: String
)

object LunarCalculator {

    // Known New Moon reference epoch: Jan 6, 2000 18:14 UTC (Julian Date 2451549.26)
    // In epoch milliseconds: 947182440000L
    private const val REFERENCE_NEW_MOON_MS = 947182440000L
    private const val SYNODIC_MONTH_DAYS = 29.530588853
    private const val SYNODIC_MONTH_MS = SYNODIC_MONTH_DAYS * 86400000.0
    private const val SIDEREAL_MONTH_DAYS = 27.321661

    private val ZODIAC_SIGNS = listOf(
        Triple("Aries ♈", "Fire 🜂", "Initiative & Spark"),
        Triple("Taurus ♉", "Earth 🜃", "Grounding & Manifestation"),
        Triple("Gemini ♊", "Air 🜁", "Curiosity & Synthesis"),
        Triple("Cancer ♋", "Water 🜄", "Intuition & Shadow Memory"),
        Triple("Leo ♌", "Fire 🜂", "Creative Will & Expression"),
        Triple("Virgo ♍", "Earth 🜃", "Analysis & Laboratory Craft"),
        Triple("Libra ♎", "Air 🜁", "Harmony & Balanced Judgment"),
        Triple("Scorpio ♏", "Water 🜄", "Transmutation & Catharsis"),
        Triple("Sagittarius ♐", "Fire 🜂", "Expansion & Higher Vision"),
        Triple("Capricorn ♑", "Earth 🜃", "Structure & Architecture"),
        Triple("Aquarius ♒", "Air 🜁", "Innovation & Breakthroughs"),
        Triple("Pisces ♓", "Water 🜄", "Dreams & Ethereal Flow")
    )

    /**
     * Calculates the exact lunar phase, illumination, moon age, zodiac transit,
     * and biorhythm correspondences for any given timestamp.
     */
    fun calculateLunarInfo(timestamp: Long = System.currentTimeMillis()): LunarInfo {
        val diffMs = timestamp - REFERENCE_NEW_MOON_MS
        val cycles = diffMs / SYNODIC_MONTH_MS
        val currentCycleProgress = (cycles - floor(cycles)).let { if (it < 0) it + 1.0 else it }

        val moonAgeDays = currentCycleProgress * SYNODIC_MONTH_DAYS

        // Illumination: 0% at New Moon, 100% at Full Moon
        val illuminationPercent = (((1.0 - cos(currentCycleProgress * 2.0 * PI)) / 2.0) * 100.0)
            .toInt()
            .coerceIn(0, 100)

        val isWaxing = currentCycleProgress < 0.5

        val phase = when {
            currentCycleProgress < 0.03 || currentCycleProgress >= 0.97 -> MoonPhase.NEW_MOON
            currentCycleProgress < 0.22 -> MoonPhase.WAXING_CRESCENT
            currentCycleProgress < 0.28 -> MoonPhase.FIRST_QUARTER
            currentCycleProgress < 0.47 -> MoonPhase.WAXING_GIBBOUS
            currentCycleProgress < 0.53 -> MoonPhase.FULL_MOON
            currentCycleProgress < 0.72 -> MoonPhase.WANING_GIBBOUS
            currentCycleProgress < 0.78 -> MoonPhase.LAST_QUARTER
            else -> MoonPhase.WANING_CRESCENT
        }

        // Approximate Zodiac sign calculation based on Moon's tropical longitude
        // Reference ecliptic longitude of Moon at reference epoch is ~284° (Capricorn)
        val daysSinceRef = diffMs / 86400000.0
        val moonLongitude = ((284.0 + (daysSinceRef / SIDEREAL_MONTH_DAYS) * 360.0) % 360.0).let {
            if (it < 0) it + 360.0 else it
        }
        val zodiacIndex = ((moonLongitude / 30.0).toInt()) % 12
        val (zodiacSign, zodiacElement, zodiacTheme) = ZODIAC_SIGNS[zodiacIndex]

        // Next major phase calculation (New, 1st Qtr, Full, 3rd Qtr)
        val quarterPositions = listOf(
            0.0 to "New Moon",
            0.25 to "First Quarter",
            0.50 to "Full Moon",
            0.75 to "Last Quarter",
            1.0 to "New Moon"
        )
        val nextQuarter = quarterPositions.firstOrNull { it.first > currentCycleProgress } ?: (1.0 to "New Moon")
        val progressToNext = nextQuarter.first - currentCycleProgress
        val daysUntilNext = (progressToNext * SYNODIC_MONTH_DAYS).toInt().coerceAtLeast(1)

        val (biorhythmFocus, labTiming, emotionalResonance) = when (phase) {
            MoonPhase.NEW_MOON -> Triple(
                "Nigredo & Seed Intentions (Reset & Inception)",
                "Optimal for cleansing lab instruments, beginning new software repositories, and deep meditative silence.",
                "Quiet introspection, high receptive clarity, low physical output."
            )
            MoonPhase.WAXING_CRESCENT -> Triple(
                "Momentum & Germination (Early Growth)",
                "Favorable for architectural drafting, initial prototypes, and gathering herbal/spagyric components.",
                "Emerging optimism, curiosity, and creative drive."
            )
            MoonPhase.FIRST_QUARTER -> Triple(
                "Action & Overcoming Obstacles (Decision Pivot)",
                "Best for rigorous debugging, resolving technical roadblocks, and refining alchemical distillations.",
                "Assertive determination, high cognitive stamina."
            )
            MoonPhase.WAXING_GIBBOUS -> Triple(
                "Coagulation & Refinement (Lab Creation)",
                "Ideal for compounding formulas, building core UI features, and intensive development sprints.",
                "Focused productivity, expansive intuition, vibrant confidence."
            )
            MoonPhase.FULL_MOON -> Triple(
                "Peak Illumination & Rubedo (Culmination)",
                "Peak time for card synthesis, project launches, celebrating breakthroughs, and crystal/essence charging.",
                "Heightened emotional sensitivity, vivid dream recall, psychic receptivity."
            )
            MoonPhase.WANING_GIBBOUS -> Triple(
                "Wisdom Dissemination & Synthesis (Sharing)",
                "Best for code reviews, writing permanent documentation, teaching, and gratitude rituals.",
                "Calm satisfaction, mentoring perspective, deep analytical clarity."
            )
            MoonPhase.LAST_QUARTER -> Triple(
                "Calcination & Purification (Release)",
                "Favorable for refactoring legacy code, clearing technical debt, and shedding emotional attachments.",
                "Discerning focus, critical evaluation, letting go of unneeded tasks."
            )
            MoonPhase.WANING_CRESCENT -> Triple(
                "Rest & Shadow Integration (Surrender)",
                "Ideal for restorative rest, dream logging, shadow journaling, and alchemical quietude.",
                "Ethereal contemplation, deep recuperation, subtle spiritual awareness."
            )
        }

        val transitSummary = "${phase.phaseName} in $zodiacSign ($zodiacElement)"

        return LunarInfo(
            phase = phase,
            phaseName = phase.phaseName,
            phaseSymbol = phase.symbol,
            illuminationPercentage = illuminationPercent,
            moonAgeDays = ((moonAgeDays * 10).toInt() / 10.0),
            cycleProgress = currentCycleProgress.toFloat(),
            isWaxing = isWaxing,
            zodiacSign = zodiacSign,
            zodiacElement = zodiacElement,
            astrologicalTransitSummary = transitSummary,
            biorhythmEnergyFocus = biorhythmFocus,
            alchemicalLabTiming = labTiming,
            nextMajorPhaseName = nextQuarter.second,
            daysUntilNextMajorPhase = daysUntilNext,
            emotionalResonance = emotionalResonance
        )
    }
}
