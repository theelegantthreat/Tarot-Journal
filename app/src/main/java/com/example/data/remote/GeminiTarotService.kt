package com.example.data.remote

import com.example.BuildConfig
import com.example.data.model.DrawnCard
import com.example.data.model.JournalEntry
import com.example.data.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiTarotService {

    private const val MODEL_NAME = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    suspend fun generateTarotSynthesis(
        entryType: String,
        spreadName: String,
        drawnCards: List<DrawnCard>,
        vibe: String,
        question: String,
        hypothesis: String,
        moonPhase: String,
        emotionalRating: Float
    ): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        val cardDetails = drawnCards.joinToString("\n") { drawn ->
            val card = DeckRepository.getCardById(drawn.cardId)
            val orientation = if (drawn.isReversed) "REVERSED" else "UPRIGHT"
            "- Position [${drawn.positionName}]: ${card.name} ($orientation). Element: ${card.element.label}, Astrology: ${card.astrologyTransit}. Meaning: ${if (drawn.isReversed) card.reversedMeaning else card.uprightMeaning}"
        }

        val prompt = """
            You are a master esoteric tarot reader, alchemist, and psychological analyst. 
            Synthesize the following $entryType tarot journal reading:
            
            Reading Context:
            - Spread: $spreadName
            - Intention / Question: "$question"
            - Internal Vibe: "$vibe"
            - Moon Phase: "$moonPhase"
            - Emotional Wave Rating: $emotionalRating / 10
            - Laboratory / Technical Hypothesis: "$hypothesis"
            
            Cards Drawn:
            $cardDetails
            
            Please provide a structured, deeply insightful synthesis:
            1. Core Synthesis & Archetypal Dialogue (How the cards weave together)
            2. Real-World & Technical Integration (Practical advice for lab experiments, programming, or life milestones)
            3. Shadow & Psychological Vector (What unconscious patterns or emotional currents are being highlighted)
            4. Concrete Action & Affirmation for Today/This Cycle
            
            Keep the tone deeply supportive, poetic yet grounded, rigorous and inspiring.
        """.trimIndent()

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            // Provide high quality local wisdom engine fallback
            return@withContext Result.success(generateLocalWisdomEngineSynthesis(spreadName, drawnCards, vibe, question, emotionalRating))
        }

        try {
            val requestJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("topP", 0.95)
                })
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().put("text", "You are an expert hermetic tarot synthesis engine providing deep alchemical and practical journal guidance."))
                    })
                })
            }

            val requestBody = requestJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val url = "$BASE_URL/$MODEL_NAME:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext Result.success(
                    generateLocalWisdomEngineSynthesis(spreadName, drawnCards, vibe, question, emotionalRating)
                )
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text")

            if (!text.isNullOrBlank()) {
                Result.success(text)
            } else {
                Result.success(generateLocalWisdomEngineSynthesis(spreadName, drawnCards, vibe, question, emotionalRating))
            }
        } catch (e: Exception) {
            Result.success(generateLocalWisdomEngineSynthesis(spreadName, drawnCards, vibe, question, emotionalRating))
        }
    }

    suspend fun generateDailyAffirmation(
        card: com.example.data.model.TarotCard,
        isReversed: Boolean
    ): Result<String> = withContext(Dispatchers.IO) {
        val apiKey = try {
            BuildConfig.GEMINI_API_KEY
        } catch (e: Exception) {
            ""
        }

        val orientation = if (isReversed) "Reversed" else "Upright"
        val prompt = """
            Create an inspiring, poetic, and grounding 1-2 sentence daily affirmation based on the Tarot Card of the Day:
            - Card: ${card.name} ($orientation)
            - Archetype & Meaning: ${if (isReversed) card.reversedMeaning else card.uprightMeaning}
            - Element: ${card.element.label}
            - Astrological Transit: ${card.astrologyTransit}
            
            Return ONLY the affirmation text directly (no quotation marks or meta commentary).
        """.trimIndent()

        if (apiKey.isBlank() || apiKey == "MY_GEMINI_API_KEY") {
            return@withContext Result.success(getLocalFallbackAffirmation(card, isReversed))
        }

        try {
            val requestJson = JSONObject().apply {
                put("contents", JSONArray().apply {
                    put(JSONObject().apply {
                        put("parts", JSONArray().apply {
                            put(JSONObject().put("text", prompt))
                        })
                    })
                })
                put("generationConfig", JSONObject().apply {
                    put("temperature", 0.7)
                    put("maxOutputTokens", 120)
                })
            }

            val requestBody = requestJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
            val url = "$BASE_URL/$MODEL_NAME:generateContent?key=$apiKey"
            val request = Request.Builder()
                .url(url)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""

            if (!response.isSuccessful) {
                return@withContext Result.success(getLocalFallbackAffirmation(card, isReversed))
            }

            val jsonResponse = JSONObject(responseBody)
            val candidates = jsonResponse.optJSONArray("candidates")
            val firstCandidate = candidates?.optJSONObject(0)
            val content = firstCandidate?.optJSONObject("content")
            val parts = content?.optJSONArray("parts")
            val text = parts?.optJSONObject(0)?.optString("text")?.trim()

            if (!text.isNullOrBlank()) {
                Result.success(text.removeSurrounding("\""))
            } else {
                Result.success(getLocalFallbackAffirmation(card, isReversed))
            }
        } catch (e: Exception) {
            Result.success(getLocalFallbackAffirmation(card, isReversed))
        }
    }

    private fun getLocalFallbackAffirmation(card: com.example.data.model.TarotCard, isReversed: Boolean): String {
        return if (isReversed) {
            "I embrace the subtle lessons of ${card.name}, turning inward to realign my energy, release hesitation, and restore inner harmony with patience."
        } else {
            "I walk forward with the luminous strength of ${card.name}, channeling ${card.element.label.lowercase()} clarity into intentional action and abundant growth."
        }
    }

    private fun generateLocalWisdomEngineSynthesis(
        spreadName: String,
        drawnCards: List<DrawnCard>,
        vibe: String,
        question: String,
        emotionalRating: Float
    ): String {
        val primaryCard = drawnCards.firstOrNull()?.let { DeckRepository.getCardById(it.cardId) }
            ?: DeckRepository.allCards.first()
        val isReversed = drawnCards.firstOrNull()?.isReversed == true

        return buildString {
            appendLine("✦ ALCHEMICAL SYNTHESIS FOR $spreadName ✦\n")
            appendLine("1. Archetypal Resonance: The presence of ${primaryCard.name} (${if (isReversed) "Reversed" else "Upright"}) signals an active ${primaryCard.element.label} current (${primaryCard.astrologyTransit}).")
            appendLine("   Correspondences: ${primaryCard.alchemicalCorrespondence}.")
            appendLine("   Your internal vibe of \"$vibe\" resonates directly with the archetype of ${primaryCard.numerologyMeaning}.\n")
            appendLine("2. Real-World & Technical Integration:")
            appendLine("   ${primaryCard.advice}")
            appendLine("   In your laboratory benchmarks and programming workflows, watch for how this energy guides clear decision-making.\n")
            appendLine("3. Emotional Wave Alignment:")
            appendLine("   With an emotional energy rating of ${"%.1f".format(emotionalRating)}/10, maintain steady pacing. ${if (isReversed) "The reversed orientation suggests an internal integration phase before outward deployment." else "The upright flow supports direct creative momentum."}\n")
            appendLine("4. Daily Action Anchor:")
            appendLine("   Honor this alignment by setting a clear boundary on your focus and executing your daily manifestation hypothesis with calm precision.")
        }
    }
}
