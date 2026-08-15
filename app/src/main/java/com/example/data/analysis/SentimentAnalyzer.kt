package com.example.data.analysis

import kotlin.math.max
import kotlin.math.min

data class SentimentAnalysisResult(
    val score: Float, // -1.0 to +1.0
    val label: String,
    val intensity: Float, // 0.0 to 1.0
    val introspectionDepth: Float, // 0.0 to 1.0
    val detectedThemes: List<String>,
    val emotionalWaveValue: Float, // 1.0 to 10.0
    val summaryInsight: String
)

object SentimentAnalyzer {

    private val highPositiveWords = setOf(
        "breakthrough", "clarity", "triumph", "joy", "radiant", "harmony", "alignment",
        "peace", "inspired", "manifestation", "abundant", "elevated", "flow", "mastery",
        "transcendent", "illuminated", "serene", "vital", "sovereign", "conjunction",
        "gratitude", "energized", "successful", "golden", "alchemical", "coherent"
    )

    private val reflectiveWords = setOf(
        "subconscious", "dream", "shadow", "meditation", "introspection", "anchor",
        "insight", "contemplation", "transmutation", "silence", "mirror", "surrender",
        "deciphering", "intuition", "resonance", "synchronicity", "archetype", "depth"
    )

    private val challengingWords = setOf(
        "obstacle", "roadblock", "friction", "anxiety", "trigger", "fatigue", "fear",
        "deadlock", "crash", "tension", "burden", "overwhelm", "blockage", "doubt",
        "unmet", "stagnancy", "loss", "grief", "turbulent", "chaos", "heavy", "burnout"
    )

    private val technicalKeywords = setOf(
        "code", "algorithm", "architecture", "debug", "laboratory", "experiment",
        "milestone", "spagyric", "calcination", "separation", "coagulation", "database",
        "framework", "deploy", "build", "refactor", "benchmark", "prototype"
    )

    fun analyze(
        vibe: String,
        intuitiveHit: String,
        manifestationHypothesis: String,
        eveningReflection: String,
        shadowWork: String,
        emotionalWaveRating: Float
    ): SentimentAnalysisResult {
        val combinedText = "$vibe $intuitiveHit $manifestationHypothesis $eveningReflection $shadowWork".lowercase()
        val tokens = combinedText.split(Regex("[\\s,;.!?:()\\[\\]\"]+")).filter { it.isNotBlank() }

        var positiveHits = 0
        var reflectiveHits = 0
        var challengeHits = 0
        var technicalHits = 0

        for (token in tokens) {
            if (token in highPositiveWords) positiveHits++
            if (token in reflectiveWords) reflectiveHits++
            if (token in challengingWords) challengeHits++
            if (token in technicalKeywords) technicalHits++
        }

        val totalKeywords = max(1, positiveHits + challengeHits + reflectiveHits)
        val rawPolarity = (positiveHits - challengeHits).toFloat() / totalKeywords.toFloat()

        // Normalize with emotionalWaveRating (1 to 10 scale where 5.5 is neutral)
        val waveNormalized = (emotionalWaveRating - 5.5f) / 4.5f
        val blendedScore = ((rawPolarity * 0.6f) + (waveNormalized * 0.4f)).coerceIn(-1.0f, 1.0f)

        val intensity = ((positiveHits + challengeHits + reflectiveHits).toFloat() / (max(10, tokens.size).toFloat() * 0.3f))
            .coerceIn(0.1f, 1.0f)

        val introspectionDepth = (reflectiveHits.toFloat() / max(1, reflectiveHits + 3)).coerceIn(0.2f, 1.0f)

        val themes = mutableListOf<String>()
        if (positiveHits > 0) themes.add("Creative Radiance")
        if (reflectiveHits > 0) themes.add("Deep Introspection")
        if (challengeHits > 0) themes.add("Shadow Catharsis")
        if (technicalHits > 0) themes.add("Alchemical Execution")
        if (emotionalWaveRating >= 8.0f) themes.add("High Vital Energy")
        if (emotionalWaveRating <= 4.0f) themes.add("Rest & Renewal")
        if (themes.isEmpty()) themes.add("Balanced Reflection")

        val label = when {
            blendedScore >= 0.6f -> "High Alchemical Radiance & Flow"
            blendedScore >= 0.2f -> "Grounded Clarity & Harmonious Focus"
            blendedScore >= -0.2f -> "Centering Equilibrium & Contemplation"
            blendedScore >= -0.6f -> "Dynamic Tension & Shadow Integration"
            else -> "Cathartic Release & Deep Rest"
        }

        val summary = buildString {
            append("Emotional wave rated at ${"%.1f".format(emotionalWaveRating)}/10. ")
            if (blendedScore >= 0.3f) {
                append("Strong energetic momentum with constructive alignment in creative work.")
            } else if (blendedScore <= -0.3f) {
                append("Noted emotional friction and shadow processing; favorable for alchemical purification.")
            } else {
                append("Steady equilibrium balancing mental inquiry and grounded observation.")
            }
            if (technicalHits > 0) {
                append(" Active integration with lab and technical milestones detected.")
            }
        }

        return SentimentAnalysisResult(
            score = blendedScore,
            label = label,
            intensity = intensity,
            introspectionDepth = introspectionDepth,
            detectedThemes = themes,
            emotionalWaveValue = emotionalWaveRating,
            summaryInsight = summary
        )
    }
}
