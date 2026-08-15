package com.example.data.repository

import com.example.data.model.Element
import com.example.data.model.Suit
import com.example.data.model.TarotCard

object DeckRepository {

    val allCards: List<TarotCard> by lazy {
        buildMajorArcana() + buildMinorArcana()
    }

    fun getCardById(id: Int): TarotCard {
        return allCards.find { it.id == id } ?: allCards.first()
    }

    fun getRandomCard(excludeIds: Set<Int> = emptySet()): TarotCard {
        val available = allCards.filter { it.id !in excludeIds }
        return if (available.isNotEmpty()) available.random() else allCards.random()
    }

    private fun buildMajorArcana(): List<TarotCard> {
        return listOf(
            TarotCard(
                id = 0,
                name = "The Fool",
                number = 0,
                romanNumeral = "0",
                suit = Suit.MAJOR,
                element = Element.AIR,
                numerologyMeaning = "0 (Infinite Potential, Zero Point Energy)",
                astrologyTransit = "Uranus / Element of Air",
                uprightMeaning = "New beginnings, leap of faith, unbounded innocence, spontaneous innovation, original idea genesis.",
                reversedMeaning = "Recklessness, fear of starting, naive risk-taking, hesitation at the threshold.",
                keywords = listOf("Inception", "Pure Potential", "Spontaneity", "Genesis", "Leap"),
                alchemicalCorrespondence = "Prima Materia (The Raw Chaotic Substance before Great Work)",
                symbolDescription = "White Rose (purity), Wand with bundle (past wisdom carried light), Little Dog (instinctive guardian), Cliff edge (infinite leap).",
                advice = "Embrace the beginner's mind in your code or lab experiments. Take the bold leap into uncharted architecture."
            ),
            TarotCard(
                id = 1,
                name = "The Magician",
                number = 1,
                romanNumeral = "I",
                suit = Suit.MAJOR,
                element = Element.AIR,
                numerologyMeaning = "1 (Singularity, Individual Will, Focus)",
                astrologyTransit = "Mercury (Communication, Intellect & Dexterity)",
                uprightMeaning = "Manifestation, masterful resource utilization, concentrated willpower, technical execution, channeling vision into reality.",
                reversedMeaning = "Scattered focus, manipulative intentions, untapped potential, impostor syndrome, misdirection.",
                keywords = listOf("Manifestation", "Willpower", "Resourcefulness", "Concentration", "Mastery"),
                alchemicalCorrespondence = "Mercury / Quicksilver (The fluid medium binding spirit and matter)",
                symbolDescription = "Lemniscate (infinity sign of perpetual mental power), Wand raised to heavens and hand pointing to earth ('As above, so below'), 4 elemental tools on table.",
                advice = "You possess every tool required for your project. Channel your willpower and code the blueprint into tangible reality."
            ),
            TarotCard(
                id = 2,
                name = "The High Priestess",
                number = 2,
                romanNumeral = "II",
                suit = Suit.MAJOR,
                element = Element.WATER,
                numerologyMeaning = "2 (Duality, Receptivity, Subconscious Equilibrium)",
                astrologyTransit = "The Moon (Intuition, Subconscious & Tides)",
                uprightMeaning = "Intuitive wisdom, subconscious access, sacred mystery, esoteric knowledge, trusting inner resonance over raw logic.",
                reversedMeaning = "Ignored intuition, hidden agendas, spiritual disconnect, gossip, surface-level fixation.",
                keywords = listOf("Intuition", "Subconscious", "Mystery", "Esoteric Insight", "Receptivity"),
                alchemicalCorrespondence = "Luna / Silver (The reflective mirror of divine soul memory)",
                symbolDescription = "Pillars of Boaz and Jachin (dark and light duality), Pomegranate veil (hidden sacred mysteries), Crescent moon at feet, Tora scroll in lap.",
                advice = "Pause mental chatter and listen to gut instinct when debugging complex systems or choosing experimental variables."
            ),
            TarotCard(
                id = 3,
                name = "The Empress",
                number = 3,
                romanNumeral = "III",
                suit = Suit.MAJOR,
                element = Element.EARTH,
                numerologyMeaning = "3 (Fertility, Growth, Creative Synthesis)",
                astrologyTransit = "Venus (Beauty, Harmony, Abundance & Organic Vitality)",
                uprightMeaning = "Fertile creation, organic growth, sensory abundance, nurturing prototypes into flourishing projects, maternal grace.",
                reversedMeaning = "Creative blockage, over-dependence, burnout, neglecting self-care, smothering development.",
                keywords = listOf("Abundance", "Fertility", "Organic Growth", "Nurturing", "Creativity"),
                alchemicalCorrespondence = "Venus / Copper (The matrix of natural generative warmth)",
                symbolDescription = "Crown of twelve stars, Field of ripe golden wheat, Flowing waterfall into lush forest, Shield with Venus emblem.",
                advice = "Give your ideas the fertile environment they need to blossom naturally. Focus on elegance and nourishing user experience."
            ),
            TarotCard(
                id = 4,
                name = "The Emperor",
                number = 4,
                romanNumeral = "IV",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "4 (Structure, Stability, Foundation, Order)",
                astrologyTransit = "Aries (Cardinal Fire, Initiative & Authority)",
                uprightMeaning = "Systemic architecture, decisive authority, disciplined boundaries, robust frameworks, reliable execution.",
                reversedMeaning = "Rigid tyranny, lack of discipline, micromanagement, crumbling infrastructure, stubborn inflexibility.",
                keywords = listOf("Structure", "Authority", "Discipline", "Architecture", "Stability"),
                alchemicalCorrespondence = "Sulphur / Fire of Aries (The organizing, fixing principle)",
                symbolDescription = "Stone throne carved with ram heads, Orb and Ankh scepter, Impassive armor beneath crimson cloak, Rugged red mountains.",
                advice = "Establish clear architectural boundaries, clean database schemas, and uncompromising standards of operational rigor."
            ),
            TarotCard(
                id = 5,
                name = "The Hierophant",
                number = 5,
                romanNumeral = "V",
                suit = Suit.MAJOR,
                element = Element.EARTH,
                numerologyMeaning = "5 (Transmission, Bridge between Finite and Infinite)",
                astrologyTransit = "Taurus (Fixed Earth, Tradition & Enduring Value)",
                uprightMeaning = "Structured lineage, proven methodologies, mentorship, academic rigor, canonical standards and sacred rituals.",
                reversedMeaning = "Dogmatic orthodoxy, rigid gatekeeping, unconventional wisdom needed, rebellious breakthrough.",
                keywords = listOf("Tradition", "Mentorship", "Lineage", "Standardization", "Wisdom"),
                alchemicalCorrespondence = "Calcination of Earth (Fixing subtle truths into institutional form)",
                symbolDescription = "Triple crown and triple cross scepter, Two crossed keys of spiritual deciphering, Two acolytes receiving sacred instruction.",
                advice = "Honor established design patterns and canonical algorithms before inventing custom overrides. Learn from master lineages."
            ),
            TarotCard(
                id = 6,
                name = "The Lovers",
                number = 6,
                romanNumeral = "VI",
                suit = Suit.MAJOR,
                element = Element.AIR,
                numerologyMeaning = "6 (Harmony, Choice, Alchemical Conjunction)",
                astrologyTransit = "Gemini (Mutable Air, Connection & Synthesis)",
                uprightMeaning = "Sacred union of opposites, values alignment, critical soul choices, harmonious collaboration, complementary synthesis.",
                reversedMeaning = "Internal conflict, misalignment of core values, fractured partnership, disharmony, indecision.",
                keywords = listOf("Alignment", "Choice", "Conjunction", "Values", "Union"),
                alchemicalCorrespondence = "Coniunctio / Sacred Chemical Marriage (King and Queen unification)",
                symbolDescription = "Archangel Raphael pouring blessing, Tree of Life and Tree of Knowledge, Volcanic mountain of passion and resolution.",
                advice = "Align your technical choices with your deepest ethical ethos. Marry intuitive feeling with crystalline logical execution."
            ),
            TarotCard(
                id = 7,
                name = "The Chariot",
                number = 7,
                romanNumeral = "VII",
                suit = Suit.MAJOR,
                element = Element.WATER,
                numerologyMeaning = "7 (Victory, Spiritual Will over Matter, Mastery)",
                astrologyTransit = "Cancer (Cardinal Water, Protective Drive)",
                uprightMeaning = "Triumphant momentum, disciplined focus steering opposing forces, rapid milestone completion, unwavering determination.",
                reversedMeaning = "Loss of control, erratic aggression, burnout, derailed timeline, conflicting team directions.",
                keywords = listOf("Drive", "Victory", "Momentum", "Focus", "Self-Mastery"),
                alchemicalCorrespondence = "Sublimation (Elevating dense physical drive into higher velocity)",
                symbolDescription = "Canopy of celestial stars, Two opposing sphinxes (black & white) steered by sheer will, Armored warrior holding star wand.",
                advice = "Harness conflicting priorities and steer them toward your singular release milestone with decisive momentum."
            ),
            TarotCard(
                id = 8,
                name = "Strength",
                number = 8,
                romanNumeral = "VIII",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "8 (Infinite Resonance, Flowing Power, Equilibrium)",
                astrologyTransit = "Leo (Fixed Fire, Courage & Radiant Heart)",
                uprightMeaning = "Gentle mastery, moral courage, compassionate patience, taming instinct with love, resilient endurance.",
                reversedMeaning = "Self-doubt, raw reactivity, suppressed rage, exhaustion, loss of confidence.",
                keywords = listOf("Courage", "Gentle Power", "Patience", "Resilience", "Compassion"),
                alchemicalCorrespondence = "Digestion / Warm Alchemical Incubation (Gentle transformation)",
                symbolDescription = "Maiden closing lion's jaws with flower garlands, Lemniscate above head, Serene green meadow.",
                advice = "Tame difficult bugs and stubborn technical challenges not with brute frustration, but with patient, methodical gentleness."
            ),
            TarotCard(
                id = 9,
                name = "The Hermit",
                number = 9,
                romanNumeral = "IX",
                suit = Suit.MAJOR,
                element = Element.EARTH,
                numerologyMeaning = "9 (Culmination, Solitary Attainment, Wisdom)",
                astrologyTransit = "Virgo (Mutable Earth, Discrimination & Deep Analysis)",
                uprightMeaning = "Solitary deep work, inner illumination, rigorous introspection, lantern of truth guiding through darkness, contemplative research.",
                reversedMeaning = "Isolation, loneliness, anti-social withdrawal, dogmatic obsession, refusing community wisdom.",
                keywords = listOf("Introspection", "Solitude", "Deep Work", "Lantern of Truth", "Research"),
                alchemicalCorrespondence = "Distillation (Extracting the pure quintessential drop in silence)",
                symbolDescription = "Old sage in grey cloak standing atop snowy mountain peak, Golden lantern holding six-pointed star of Solomon, Staff of authority.",
                advice = "Enter deep focus mode. Silence notifications, retreat into your inner laboratory, and distill the pure solution."
            ),
            TarotCard(
                id = 10,
                name = "Wheel of Fortune",
                number = 10,
                romanNumeral = "X",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "10 (Cyclical Transition, Quantum Shift, New Octave)",
                astrologyTransit = "Jupiter (Expansion, Synchronicity & Fortune)",
                uprightMeaning = "Karmic cycles, auspicious timing, turning points, sudden breakthroughs, synchronicity, honoring natural rhythms.",
                reversedMeaning = "Unfortunate delays, resistance to inevitable change, clinging to obsolete frameworks, temporary setback.",
                keywords = listOf("Cycles", "Synchronicity", "Breakthrough", "Destiny", "Turning Point"),
                alchemicalCorrespondence = "Circulatio (The perpetual circulation of matter in the alembic)",
                symbolDescription = "Golden wheel inscribed with TARO / ROTA, Four living winged creatures in corners studying sacred books, Sphinx atop wheel, Anubis ascending.",
                advice = "Recognize when the tide is in your favor and deploy boldly. Stay centered at the wheel's still hub amid fluctuating external cycles."
            ),
            TarotCard(
                id = 11,
                name = "Justice",
                number = 11,
                romanNumeral = "XI",
                suit = Suit.MAJOR,
                element = Element.AIR,
                numerologyMeaning = "11 (Mastery of Balance, Karmic Equilibrium)",
                astrologyTransit = "Libra (Cardinal Air, Objective Truth & Fairness)",
                uprightMeaning = "Objective truth, cause and effect, balanced calibration, clear legal / architectural boundaries, ethical clarity.",
                reversedMeaning = "Bias, dishonesty, unfair critique, unaccountability, unbalanced codebase / technical debt.",
                keywords = listOf("Truth", "Equilibrium", "Objectivity", "Accountability", "Clarity"),
                alchemicalCorrespondence = "Coagulation & Equilibrium (Balanced weighing of reactants)",
                symbolDescription = "Upright two-edged sword of mental discernment in right hand, Golden scales of balance in left hand, Violet veil of justice.",
                advice = "Run objective benchmarks without confirmation bias. Balance your system's load, refactor technical debt, and maintain pristine integrity."
            ),
            TarotCard(
                id = 12,
                name = "The Hanged Man",
                number = 12,
                romanNumeral = "XII",
                suit = Suit.MAJOR,
                element = Element.WATER,
                numerologyMeaning = "12 (Sacred Surrender, Reversal of Perspective)",
                astrologyTransit = "Neptune / Element of Water",
                uprightMeaning = "Paradoxical insight, voluntary pause, seeing upside down, releasing control, transcendent enlightenment through surrender.",
                reversedMeaning = "Pointless stalling, martyrdom, stubborn resistance to letting go, ego frustration.",
                keywords = listOf("Surrender", "New Perspective", "Stillness", "Paradox", "Suspension"),
                alchemicalCorrespondence = "Putrefaction (Dormancy preceding the rebirth of gold)",
                symbolDescription = "Figure suspended upside down from living wooden T-cross by one foot, Golden halo glowing around head, Serene and peaceful expression.",
                advice = "When stuck on an intractable bug, stop forcing a solution. Step away into stillness and view the problem inverted."
            ),
            TarotCard(
                id = 13,
                name = "Death",
                number = 13,
                romanNumeral = "XIII",
                suit = Suit.MAJOR,
                element = Element.WATER,
                numerologyMeaning = "13 (Metamorphosis, Cleansing, Renewal)",
                astrologyTransit = "Scorpio (Fixed Water, Radical Transformation)",
                uprightMeaning = "Profound transformation, necessary ending, clearing decayed systems to birth new structures, alchemical metamorphosis.",
                reversedMeaning = "Fear of change, clinging to dead projects, stagnancy, painful dragged-out transitions.",
                keywords = listOf("Transformation", "Ending", "Rebirth", "Metamorphosis", "Clearing"),
                alchemicalCorrespondence = "Mortificatio / Nigredo (The Black Stage of absolute purification)",
                symbolDescription = "Armored skeleton knight riding white horse with black banner bearing five-petal Mystic Rose, Rising sun between twin pillars.",
                advice = "Deprecate obsolete legacy code ruthlessly. Clear out dead paradigms so the vibrant new architecture can flourish."
            ),
            TarotCard(
                id = 14,
                name = "Temperance",
                number = 14,
                romanNumeral = "XIV",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "14 (Harmonious Blending, Moderate Fusion)",
                astrologyTransit = "Sagittarius (Mutable Fire, Higher Purpose)",
                uprightMeaning = "Alchemical synthesis, optimal calibration, patience, blending disparate technologies harmoniously, divine middle path.",
                reversedMeaning = "Extremes, imbalance, rushed experiments, volatile clashes of personality or tech stacks.",
                keywords = listOf("Synthesis", "Balance", "Alchemy", "Moderation", "Integration"),
                alchemicalCorrespondence = "Separatio & Conjunctio (Harmonious recombination of pure essences)",
                symbolDescription = "Winged solar angel with one foot on earth and one in water, Pouring glowing liquid continuously between two chalices.",
                advice = "Blend intuitive UX with robust back-end precision. Find the golden balance where performance and aesthetics merge."
            ),
            TarotCard(
                id = 15,
                name = "The Devil",
                number = 15,
                romanNumeral = "XV",
                suit = Suit.MAJOR,
                element = Element.EARTH,
                numerologyMeaning = "15 (Shadow Entanglement, Material Attachment)",
                astrologyTransit = "Capricorn (Cardinal Earth, Material Structure)",
                uprightMeaning = "Shadow work, recognizing unconscious addictions or limiting beliefs, material entrapment, identifying hidden toxicity.",
                reversedMeaning = "Breaking chains, shadow illumination, releasing unhealthy obsessions, reclaiming sovereignty.",
                keywords = listOf("Shadow", "Illusion", "Entanglement", "Liberation", "Attachments"),
                alchemicalCorrespondence = "Caput Mortuum (The worthless dregs to be purged)",
                symbolDescription = "Horned Baphomet figure on pedestal with inverted pentagram, Man and woman loosely chained by neck, Hand raised with forbidden sign.",
                advice = "Examine where perfectionism or ego attachment is chaining your progress. The chains are loose—step free and iterate."
            ),
            TarotCard(
                id = 16,
                name = "The Tower",
                number = 16,
                romanNumeral = "XVI",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "16 (Sudden Collapse of False Foundations)",
                astrologyTransit = "Mars (Dynamic Fire, Breakthrough Catalyst)",
                uprightMeaning = "Sudden breakthrough, destruction of false assumptions, radical revelation, necessary disruption clearing false architecture.",
                reversedMeaning = "Avoiding inevitable breakdown, delayed reckoning, fear of truth, internal crisis.",
                keywords = listOf("Breakthrough", "Disruption", "Revelation", "Demolition", "Awakening"),
                alchemicalCorrespondence = "Calcination by Lightning (Instant burning of all illusions)",
                symbolDescription = "Stone fortress blasted by golden bolt of lightning, Golden crown falling, Flames bursting from windows, Figures tumbling into freedom.",
                advice = "Welcome the unexpected system crash or paradigm shift as a gift. It exposes the hidden flaw before production scaling."
            ),
            TarotCard(
                id = 17,
                name = "The Star",
                number = 17,
                romanNumeral = "XVII",
                suit = Suit.MAJOR,
                element = Element.AIR,
                numerologyMeaning = "17 (Hope, Inspiration, Cosmic Guidance)",
                astrologyTransit = "Aquarius (Fixed Air, Visionary Future)",
                uprightMeaning = "Renewed hope, luminous inspiration, clarity, spiritual serenity, faith in the long-term vision, unblocked creative flow.",
                reversedMeaning = "Despair, cynicism, creative drought, lost faith, disconnected from inspiration.",
                keywords = listOf("Inspiration", "Hope", "Serenity", "Vision", "Clarity"),
                alchemicalCorrespondence = "Albedo (The Whitening / Luminescence of spiritual clarity)",
                symbolDescription = "Nude maiden pouring celestial water onto land and stream from two urns, Large eight-pointed star of gold surrounded by seven smaller stars, Ibis bird in tree.",
                advice = "Trust your visionary instincts. Drink from the pure spring of creative optimism and share your luminous findings."
            ),
            TarotCard(
                id = 18,
                name = "The Moon",
                number = 18,
                romanNumeral = "XVIII",
                suit = Suit.MAJOR,
                element = Element.WATER,
                numerologyMeaning = "18 (Subconscious Tides, Illusion & Astral Vision)",
                astrologyTransit = "Pisces (Mutable Water, Dreams & The Unconscious)",
                uprightMeaning = "Subconscious exploration, dream deciphering, navigating ambiguity, honoring emotional waves, uncovering hidden depths.",
                reversedMeaning = "Confusion lifting, conquering irrational fears, clarity emerging from fog, deception revealed.",
                keywords = listOf("Dreams", "Intuition", "Ambiguity", "Subconscious", "Illusion"),
                alchemicalCorrespondence = "Solutio (Dissolving in the unconscious ocean of archetypes)",
                symbolDescription = "Full moon with face emitting dew drops, Two towers on horizon, Dog and wolf howling, Crayfish emerging from dark waters onto winding path.",
                advice = "Pay close attention to tonight's dreams and subtle atmospheric shifts. Do not rush to judgment while the mist is thick."
            ),
            TarotCard(
                id = 19,
                name = "The Sun",
                number = 19,
                romanNumeral = "XIX",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "19 (Radiant Wholeness, Vitality, Joy)",
                astrologyTransit = "The Sun (Solar Center, Vital Force & Truth)",
                uprightMeaning = "Radiant clarity, vitality, glorious triumph, joyful manifestation, transparent truth, effortless creative warmth.",
                reversedMeaning = "Temporary clouded optimism, inner child wounded, delayed celebration, mild fatigue.",
                keywords = listOf("Radiance", "Vitality", "Triumph", "Joy", "Clarity"),
                alchemicalCorrespondence = "Citrinitas / Gold Manifest (The Solar Gold of realization)",
                symbolDescription = "Huge beaming sun with straight and wavy rays, Joyful child crowned with flowers riding white horse, Wall of blooming sunflowers.",
                advice = "Celebrate your progress with pure, unreserved joy. Share your work openly in the warm light of day."
            ),
            TarotCard(
                id = 20,
                name = "Judgement",
                number = 20,
                romanNumeral = "XX",
                suit = Suit.MAJOR,
                element = Element.FIRE,
                numerologyMeaning = "20 (Awakening, Reckoning, Highest Calling)",
                astrologyTransit = "Pluto / Element of Fire",
                uprightMeaning = "Soul awakening, answered calling, comprehensive retrospective synthesis, absolute self-forgiveness, higher evolutionary level.",
                reversedMeaning = "Self-criticism, ignoring the inner calling, fear of judgment, repeating past mistakes.",
                keywords = listOf("Awakening", "Calling", "Synthesis", "Reckoning", "Elevation"),
                alchemicalCorrespondence = "Fermentatio (Awakening the spiritual yeast of higher purpose)",
                symbolDescription = "Archangel Gabriel sounding golden trumpet with cross banner, Figures rising with outstretched arms from stone sarcophagi.",
                advice = "Conduct your weekly retrospective with compassionate truth. Rise up and answer the higher calling of your craftsmanship."
            ),
            TarotCard(
                id = 21,
                name = "The World",
                number = 21,
                romanNumeral = "XXI",
                suit = Suit.MAJOR,
                element = Element.EARTH,
                numerologyMeaning = "21 (Cosmic Completion, Wholeness, Integration)",
                astrologyTransit = "Saturn (Structure & Cosmic Culmination)",
                uprightMeaning = "Grand cycle completion, holistic mastery, integration of all elements, triumphant graduation, cosmic alignment.",
                reversedMeaning = "Incomplete closure, shortcuts taken, missing final polish, lingering loose ends.",
                keywords = listOf("Wholeness", "Completion", "Mastery", "Integration", "Triumph"),
                alchemicalCorrespondence = "Rubedo / The Magnum Opus (The completed Philosopher's Stone)",
                symbolDescription = "Dancing figure with twin wands surrounded by green laurel wreath bound with red lemniscate ribbons, Four fixed astrological signs in corners.",
                advice = "You have completed a monumental chapter. Integrate your learnings, tie up final details, and step into cosmic wholeness."
            )
        )
    }

    private fun buildMinorArcana(): List<TarotCard> {
        val list = mutableListOf<TarotCard>()
        var id = 22

        val suits = listOf(
            Suit.WANDS to (Element.FIRE to "Fire / Spagyric Calcination & Pure Will"),
            Suit.CUPS to (Element.WATER to "Water / Intuition, Dreams & Emotional Waves"),
            Suit.SWORDS to (Element.AIR to "Air / Intellect, Logic & Coding Architecture"),
            Suit.PENTACLES to (Element.EARTH to "Earth / Physical Manifestation & Lab Reality")
        )

        val ranks = listOf(
            "Ace" to (1 to "I"),
            "Two" to (2 to "II"),
            "Three" to (3 to "III"),
            "Four" to (4 to "IV"),
            "Five" to (5 to "V"),
            "Six" to (6 to "VI"),
            "Seven" to (7 to "VII"),
            "Eight" to (8 to "VIII"),
            "Nine" to (9 to "IX"),
            "Ten" to (10 to "X"),
            "Page" to (11 to "Page"),
            "Knight" to (12 to "Knight"),
            "Queen" to (13 to "Queen"),
            "King" to (14 to "King")
        )

        for ((suit, elemPair) in suits) {
            val (element, alchem) = elemPair
            for ((rankName, numPair) in ranks) {
                val (num, roman) = numPair
                val cardName = "$rankName of ${suit.name.lowercase().replaceFirstChar { it.uppercase() }}"

                val (upright, reversed, advice, symbols) = generateMinorMeanings(rankName, suit)

                list.add(
                    TarotCard(
                        id = id++,
                        name = cardName,
                        number = num,
                        romanNumeral = roman,
                        suit = suit,
                        element = element,
                        numerologyMeaning = "$num (${getNumerologyForMinor(num, rankName)})",
                        astrologyTransit = getAstrologyForMinor(num, suit),
                        uprightMeaning = upright,
                        reversedMeaning = reversed,
                        keywords = generateKeywords(rankName, suit),
                        alchemicalCorrespondence = alchem,
                        symbolDescription = symbols,
                        advice = advice
                    )
                )
            }
        }
        return list
    }

    private fun getNumerologyForMinor(num: Int, rank: String): String {
        return when (rank) {
            "Ace" -> "Seed of Potential & Raw Elemental Origin"
            "Two" -> "Duality, Balance, Choice & Reflection"
            "Three" -> "Initial Synthesis, Creative Expansion & Group Output"
            "Four" -> "Consolidation, Foundation, Structure & Stability"
            "Five" -> "Dynamic Friction, Test of Resilience & Recalibration"
            "Six" -> "Harmonious Flow, Restoration, Victory & Reciprocity"
            "Seven" -> "Strategic Assessment, Spiritual Evaluation & Refinement"
            "Eight" -> "Velocity, Mastery, Focused Repetition & Iteration"
            "Nine" -> "Culmination, Peak Attainment & Boundary Protection"
            "Ten" -> "End of Cycle, Maximum Manifestation & Transition to Next Tier"
            "Page" -> "Eager Student, Messenger & Exploratory Prototype"
            "Knight" -> "Active Momentum, Quest Driver & Direct Action"
            "Queen" -> "Internalized Mastery, Intuitive Authority & Nurturing Engine"
            "King" -> "Externalized Sovereignty, Systems Command & Strategic Governance"
            else -> "Numerical Archetype"
        }
    }

    private fun getAstrologyForMinor(num: Int, suit: Suit): String {
        return when (suit) {
            Suit.WANDS -> when (num) {
                1 -> "Root of Fire (Aries/Leo/Sagittarius)"
                2 -> "Mars in Aries"
                3 -> "Sun in Aries"
                4 -> "Venus in Aries"
                5 -> "Saturn in Leo"
                6 -> "Jupiter in Leo"
                7 -> "Mars in Leo"
                8 -> "Mercury in Sagittarius"
                9 -> "Moon in Sagittarius"
                10 -> "Saturn in Sagittarius"
                11 -> "Earth of Fire (Page of Wands)"
                12 -> "Fire of Fire (Knight of Wands)"
                13 -> "Water of Fire (Queen of Wands)"
                14 -> "Air of Fire (King of Wands)"
                else -> "Fire Triplicity"
            }
            Suit.CUPS -> when (num) {
                1 -> "Root of Water (Cancer/Scorpio/Pisces)"
                2 -> "Venus in Cancer"
                3 -> "Mercury in Cancer"
                4 -> "Moon in Cancer"
                5 -> "Mars in Scorpio"
                6 -> "Sun in Scorpio"
                7 -> "Venus in Scorpio"
                8 -> "Saturn in Pisces"
                9 -> "Jupiter in Pisces"
                10 -> "Mars in Pisces"
                11 -> "Earth of Water (Page of Cups)"
                12 -> "Fire of Water (Knight of Cups)"
                13 -> "Water of Water (Queen of Cups)"
                14 -> "Air of Water (King of Cups)"
                else -> "Water Triplicity"
            }
            Suit.SWORDS -> when (num) {
                1 -> "Root of Air (Gemini/Libra/Aquarius)"
                2 -> "Moon in Libra"
                3 -> "Saturn in Libra"
                4 -> "Jupiter in Libra"
                5 -> "Venus in Aquarius"
                6 -> "Mercury in Aquarius"
                7 -> "Moon in Aquarius"
                8 -> "Jupiter in Gemini"
                9 -> "Mars in Gemini"
                10 -> "Sun in Gemini"
                11 -> "Earth of Air (Page of Swords)"
                12 -> "Fire of Air (Knight of Swords)"
                13 -> "Water of Air (Queen of Swords)"
                14 -> "Air of Air (King of Swords)"
                else -> "Air Triplicity"
            }
            Suit.PENTACLES -> when (num) {
                1 -> "Root of Earth (Taurus/Virgo/Capricorn)"
                2 -> "Jupiter in Capricorn"
                3 -> "Mars in Capricorn"
                4 -> "Sun in Capricorn"
                5 -> "Mercury in Taurus"
                6 -> "Moon in Taurus"
                7 -> "Saturn in Taurus"
                8 -> "Sun in Virgo"
                9 -> "Venus in Virgo"
                10 -> "Mercury in Virgo"
                11 -> "Earth of Earth (Page of Pentacles)"
                12 -> "Fire of Earth (Knight of Pentacles)"
                13 -> "Water of Earth (Queen of Pentacles)"
                14 -> "Air of Earth (King of Pentacles)"
                else -> "Earth Triplicity"
            }
            else -> "Astrological Transit"
        }
    }

    private fun generateMinorMeanings(rank: String, suit: Suit): Quadruple {
        return when (suit) {
            Suit.WANDS -> when (rank) {
                "Ace" -> Quadruple(
                    "Pure creative spark, high energy initiation, breakthrough motivation, vital inspiration.",
                    "Delays, lack of passion, hesitation, misdirected energy.",
                    "Ignite your project today. Strike while the creative fire is burning hot.",
                    "Sprouting wand held by celestial hand from cloud, falling yod leaves of divine blessing."
                )
                "Two" -> Quadruple(
                    "Strategic foresight, global planning, bold decisions, holding the world in your hands.",
                    "Fear of the unknown, hesitation to scale, insular thinking.",
                    "Plan your roadmap with grand ambition. The vision is ready for broader execution.",
                    "Figure on castle battlements gazing at ocean holding a globe in right hand and staff in left."
                )
                "Three" -> Quadruple(
                    "Ships coming in, long-range progress, expansion, collaborative validation.",
                    "Obstacles in shipping, delayed deliveries, frustrated expectations.",
                    "Look to the horizon. Your previous iterations are beginning to yield tangible fruit.",
                    "Traveler looking out over golden sea where three ships sail toward safe harbor."
                )
                "Four" -> Quadruple(
                    "Celebration of milestones, stability, harmonious foundation, community resonance.",
                    "Transient joy, slight disruption, milestone delayed but within reach.",
                    "Celebrate your recent milestone. Rest on solid ground before the next build.",
                    "Four floral-garlanded wands forming a canopy with two celebrating figures in background."
                )
                "Five" -> Quadruple(
                    "Dynamic competition, brainstorming conflict, testing ideas through rigorous debate.",
                    "Destructive hostility, avoidance of healthy critique, petty bickering.",
                    "Embrace constructive tension. Refine your system by letting ideas clash productively.",
                    "Five youths playfully or competitively clashing wands in dynamic simulation."
                )
                "Six" -> Quadruple(
                    "Public triumph, peer recognition, validated breakthrough, victory lap.",
                    "Ego inflation, fall from grace, unacknowledged contributions.",
                    "Share your success and honor the team and mentors who made the victory possible.",
                    "Crowned horseman riding through cheering throng carrying laurel-wreathed staff."
                )
                "Seven" -> Quadruple(
                    "Holding high ground, defending core architecture, courage under pressure.",
                    "Overwhelmed by competition, giving up defensible advantage, exhaustion.",
                    "Stand firm on your architectural decisions. Do not compromise core principles.",
                    "Defiant figure standing atop hill with staff defending position against six opposing wands."
                )
                "Eight" -> Quadruple(
                    "High-speed velocity, rapid deployment, clear flight path, messages incoming.",
                    "Chaotic rushing, missed communications, reckless speed causing bugs.",
                    "Move fast with clarity. The channel is open for rapid execution and smooth data flow.",
                    "Eight straight wands flying parallel through clear open sky toward lush green riverbank."
                )
                "Nine" -> Quadruple(
                    "Resilience at the finish line, guarding hard-won milestones, battle-tested strength.",
                    "Paranoia, defensive exhaustion, stubborn refusal to accept assistance.",
                    "You are on the final lap. Protect your energy and push through to the release.",
                    "Bandaged sentinel standing firm holding staff before defensive palisade of eight wands."
                )
                "Ten" -> Quadruple(
                    "Heavy burden, over-commitment, carrying the full system load alone, final push.",
                    "Delegating excess load, collapse from burnout, letting go of unneeded baggage.",
                    "Offload unnecessary modules or delegate tasks. Lighten the load before scaling.",
                    "Figure carrying heavy bundle of ten heavy wands toward distant village."
                )
                "Page" -> Quadruple(
                    "Exploratory enthusiasm, eager messenger, fresh technical concept, playful prototype.",
                    "Superficial curiosity, gossip, lack of follow-through on new ideas.",
                    "Tinker and prototype without fear of failure. Let youthful curiosity guide your day.",
                    "Young herald holding sprouting staff in desert gazing up with eager curiosity."
                )
                "Knight" -> Quadruple(
                    "Fierce drive, impulsive action, passionate crusade, accelerating delivery.",
                    "Impulsive burnout, scattershot aggression, reckless disregard for safeguards.",
                    "Channel your passion into focused action. Deliver with bold confidence.",
                    "Armored knight in flame-embroidered tunic charging forward on galloping steed."
                )
                "Queen" -> Quadruple(
                    "Radiant confidence, charismatic warmth, magnetic creative power, fierce loyalty.",
                    "Jealousy, demanding temperament, burnout of inner radiance.",
                    "Command your domain with warmth and poise. Lead by inspiring those around you.",
                    "Crowned queen holding sunflower and black cat sitting on lion-embellished throne."
                )
                "King" -> Quadruple(
                    "Visionary governance, systemic leadership, master strategist, blazing inspiration.",
                    "Autocratic dictation, unrealistic demands, impatient temper.",
                    "Set the overarching vision and empower your systems to execute autonomously.",
                    "King holding living wand seated on throne adorned with lions and ouroboros salamanders."
                )
                else -> Quadruple("Fire energy", "Fire block", "Channel fire", "Wands")
            }
            Suit.CUPS -> when (rank) {
                "Ace" -> Quadruple(
                    "Overflowing intuition, emotional renewal, heart-centered inspiration, deep peace.",
                    "Emotional drain, blocked empathy, creative drought, repressed feelings.",
                    "Open your heart to intuitive resonance. Let empathy guide your design.",
                    "Golden chalice overflowing with five streams of water, dove placing sacred wafer."
                )
                "Two" -> Quadruple(
                    "Deep mutual understanding, harmonious connection, soul communion, collaborative trust.",
                    "Communication breakdown, mismatched expectations, codependency.",
                    "Cultivate deep trust in your partnerships. Synergy multiplies individual capacity.",
                    "Man and woman exchanging floral-crowned cups beneath winged lion and caduceus."
                )
                "Three" -> Quadruple(
                    "Community celebration, collective creativity, shared joy, support circle.",
                    "Overindulgence, gossip, feeling left out of inner circles.",
                    "Celebrate creative camaraderie. Share your discoveries with your peers.",
                    "Three maidens dancing in circle raising golden cups in vineyard harvest."
                )
                "Four" -> Quadruple(
                    "Contemplative detachment, meditation pause, reevaluating opportunities, apathy.",
                    "Snapping out of funk, noticing fresh opportunities, renewing enthusiasm.",
                    "Reflect on what truly matters before accepting new commitments.",
                    "Figure sitting under tree cross-armed contemplating three cups while fourth is offered by cloud."
                )
                "Five" -> Quadruple(
                    "Grieving setbacks, processing loss, shifting gaze from spilled cups to standing ones.",
                    "Acceptance, emotional healing, realizing resources still remain.",
                    "Acknowledge the failed experiment, learn the lesson, and pivot to remaining assets.",
                    "Cloaked figure gazing in sorrow at three spilled cups, unaware of two full cups behind."
                )
                "Six" -> Quadruple(
                    "Nostalgic sweetness, childhood innocence, revisiting roots, wholesome kindness.",
                    "Living in past, clinging to obsolete nostalgia, unresolved childhood triggers.",
                    "Reconnect with the pure, playful reasons you first fell in love with your craft.",
                    "Boy offering cup of white flowers to young girl in courtyard of ancient castle."
                )
                "Seven" -> Quadruple(
                    "Vivid imagination, multiple possibilities, visionary dreaming, discerning illusion from real.",
                    "Overwhelmed by choices, paralysis by analysis, deceptive mirages.",
                    "Filter the multitude of ideas through reality testing. Select the golden option.",
                    "Figure in silhouette viewing seven mystical gifts floating in ethereal clouds."
                )
                "Eight" -> Quadruple(
                    "Conscious departure, walking away from unfulfilling situations, quest for higher truth.",
                    "Fear of leaving, lingering in dead ends, wandering without aim.",
                    "Have the courage to walk away from designs that no longer serve your vision.",
                    "Cloaked traveler with staff walking away by moonlight from eight stacked cups toward mountains."
                )
                "Nine" -> Quadruple(
                    "Wish fulfillment, emotional satisfaction, content wholeness, gratitude.",
                    "Smug complacency, overindulgence, superficial satisfaction.",
                    "Bask in deep gratitude for your progress. You have arrived at a place of fulfillment.",
                    "Jovial figure seated proudly with arms folded before curved arc of nine golden cups."
                )
                "Ten" -> Quadruple(
                    "Radiant emotional harmony, lasting peace, family fulfillment, joyful home base.",
                    "Domestic friction, disconnected relationships, strained harmony.",
                    "Cultivate harmony in your sanctuary. An aligned environment fuels great work.",
                    "Joyful family looking toward sky where ten golden cups form rainbow arc over home."
                )
                "Page" -> Quadruple(
                    "Creative curiosity, unexpected intuitive messages, poetic sensitivity, sweet surprises.",
                    "Emotional immaturity, hypersensitivity, creative daydreams without action.",
                    "Listen to delicate intuitive whispers. Pay attention to synchronistic messages.",
                    "Youth in floral tunic holding golden cup from which curious fish peeks out."
                )
                "Knight" -> Quadruple(
                    "Romantic quest, passionate vision, following the heart's calling, graceful offers.",
                    "Overly idealistic, mood swings, chasing illusions, avoiding practical duty.",
                    "Pursue your highest aesthetic ideal with grace and dignified commitment.",
                    "Graceful knight in winged helmet riding white steed holding golden cup."
                )
                "Queen" -> Quadruple(
                    "Profound intuitive depth, psychic sensitivity, compassionate sanctuary, dream mastery.",
                    "Emotional overwhelm, codependency, blurred boundaries, moodiness.",
                    "Trust your deep intuitive readings. Act as a serene anchor of calm wisdom.",
                    "Queen gazing into intricate jeweled cup on throne at edge of ocean."
                )
                "King" -> Quadruple(
                    "Emotional equilibrium, compassionate diplomacy, master of feelings, tranquil wisdom.",
                    "Emotional manipulation, cold moodiness, suppressed volcanic feelings.",
                    "Lead with calm emotional intelligence. Navigate turbulent waves with steady poise.",
                    "King seated on stone throne floating on sea holding cup and scepter."
                )
                else -> Quadruple("Water energy", "Water block", "Channel water", "Cups")
            }
            Suit.SWORDS -> when (rank) {
                "Ace" -> Quadruple(
                    "Mental breakthrough, piercing clarity, sharp intellect, uncompromising truth.",
                    "Mental fog, harsh words, scattered thoughts, intellectual arrogance.",
                    "Cut through complexity to find the elegant, sharp truth of your problem.",
                    "Celestial hand holding upright double-edged sword crowned with laurel and palm branches."
                )
                "Two" -> Quadruple(
                    "Tense stalemate, difficult choice, blocked perception, weighing two options in stillness.",
                    "Breaking deadlock, indecision resolved, facing hard truths.",
                    "Quiet the mind, weigh the alternatives objectively, and make the needed decision.",
                    "Blindfolded woman holding two crossed heavy swords in perfect balance before calm sea."
                )
                "Three" -> Quadruple(
                    "Heartbreak, painful realization, sorrow leading to cathartic understanding, surgical truth.",
                    "Healing from grief, forgiving past mistakes, releasing trauma.",
                    "Allow the pain of a failed release to teach you. Truth cleanses and heals.",
                    "Pierced heart with three swords beneath storm clouds and driving rain."
                )
                "Four" -> Quadruple(
                    "Rest, recuperation, sanctuary from mental battles, restorative meditation.",
                    "Burnout from skipping rest, restlessness, emerging renewed from hiatus.",
                    "Take a deliberate mental hiatus. The mind repairs its best synapses in rest.",
                    "Knight's effigy lying in repose in chapel beneath three swords on wall and one beside."
                )
                "Five" -> Quadruple(
                    "Hollow victory, ego conflict, cutthroat tactics, knowing when to walk away.",
                    "Reconciliation, putting down weapons, learning from defeat.",
                    "Win the war for truth, not petty ego battles. Pick your battles wisely.",
                    "Smirking warrior holding three swords while two defeated figures walk away toward choppy sea."
                )
                "Six" -> Quadruple(
                    "Transition to calmer waters, leaving turbulent conflict behind, mental recovery.",
                    "Carrying mental baggage, rocky journey, resisted transition.",
                    "Navigate calmly out of the storm. Smoother conditions lie immediately ahead.",
                    "Ferryman poling boat carrying mother and child across quiet water with six swords standing upright."
                )
                "Seven" -> Quadruple(
                    "Stealth strategy, tactical agility, unconventional solution, keeping plans close.",
                    "Deception exposed, imposter feelings, cowardly shortcuts, getting caught.",
                    "Use clever engineering and discreet tactics. Work smart rather than loud.",
                    "Figure tiptoeing away from military camp carrying five swords while leaving two standing."
                )
                "Eight" -> Quadruple(
                    "Self-imposed mental imprisonment, perceived paralysis, overcoming limiting thoughts.",
                    "Breaking free from mental cages, self-empowerment, seeing open escape path.",
                    "The constraints holding you back are mental. Step out of the conceptual cage.",
                    "Blindfolded woman bound loosely by ropes surrounded by eight swords in muddy marsh."
                )
                "Nine" -> Quadruple(
                    "Nighttime anxiety, overthinking, catastrophizing, insomnia, mental torment.",
                    "Release from nightmare, seeing that worries were exaggerated, seeking help.",
                    "Breathe through nighttime worries. Daylight reveals that your fears were inflated.",
                    "Figure sitting up in bed with head in hands beneath nine swords mounted horizontally on black wall."
                )
                "Ten" -> Quadruple(
                    "Rock bottom, absolute conclusion of ordeal, darkness before dawn, complete closure.",
                    "Rising from ashes, recovery, worst is definitively over.",
                    "The painful ordeal is definitively finished. The only way now is upward toward the sunrise.",
                    "Figure lying prone with ten swords in back beneath black sky with golden sunrise on horizon."
                )
                "Page" -> Quadruple(
                    "Sharp intellect, agile problem solving, vigilant curiosity, clear-eyed researcher.",
                    "Cynicism, argumentative pedantry, hasty conclusions without data.",
                    "Keep your analytical radar sharp. Question assumptions with vibrant mental agility.",
                    "Youth holding sword upright with both hands in gusty wind over rough terrain."
                )
                "Knight" -> Quadruple(
                    "Furious intellectual speed, razor logic, daring crusade, cutting through bottlenecks.",
                    "Reckless words, intellectual bullying, charging without a plan.",
                    "Attack the problem with crystalline logic and rapid diagnostic precision.",
                    "Knight charging at full speed with drawn sword into battle against storm clouds."
                )
                "Queen" -> Quadruple(
                    "Piercing discernment, direct communication, objective wisdom, uncompromising clarity.",
                    "Cold cynicism, bitter hypercriticality, emotional detachment.",
                    "Communicate with crisp, elegant brevity. Be fair, objective, and truthful.",
                    "Queen seated on stone throne in profile extending right hand, holding upright sword in left."
                )
                "King" -> Quadruple(
                    "Intellectual sovereignty, master of systems and law, strategic command, clarity of thought.",
                    "Tyrannical intellect, cold manipulation, ruthless micromanagement.",
                    "Architect your systems with master-level governance and rigorous logical integrity.",
                    "King seated squarely on throne holding upright sword of judgment in purple robe."
                )
                else -> Quadruple("Air energy", "Air block", "Channel air", "Swords")
            }
            Suit.PENTACLES -> when (rank) {
                "Ace" -> Quadruple(
                    "Tangible seed of wealth, physical breakthrough, laboratory harvest, real-world proof.",
                    "Lost financial opportunity, neglecting material reality, poor foundation.",
                    "Plant the seed for tangible real-world results. Ground your ideas in working code.",
                    "Celestial hand emerging from cloud holding huge golden coin over lush garden arch."
                )
                "Two" -> Quadruple(
                    "Fluid adaptability, juggling multiple priorities, graceful balance, cashflow rhythm.",
                    "Overwhelmed multitasking, dropped balls, financial disarray.",
                    "Juggle your priorities with joyful agility. Stay flexible as conditions flux.",
                    "Youth dancing in infinity-ribbon while juggling two golden pentacles as ships ride ocean waves."
                )
                "Three" -> Quadruple(
                    "Master craftsmanship, architectural collaboration, skilled execution, peer respect.",
                    "Poor workmanship, lack of teamwork, ignoring quality standards.",
                    "Build with the precision of a cathedral mason. Dedicate yourself to master craftsmanship.",
                    "Master sculptor carving stonework in cathedral with monk and architect reviewing plans."
                )
                "Four" -> Quadruple(
                    "Security, prudent conservation, establishing solid boundaries, protecting assets.",
                    "Greed, hoarding, fear of investing, rigid scarcity mindset.",
                    "Protect your core codebase and resources without strangling fresh creative circulation.",
                    "Crowned figure clasping one pentacle tightly to chest, one on head, two firmly under feet."
                )
                "Five" -> Quadruple(
                    "Temporary hardship, feeling left out in cold, resource scarcity, testing fortitude.",
                    "Recovery from loss, discovering hidden shelter, welcoming community support.",
                    "Look up from current resource limitations. The illuminated sanctuary is right beside you.",
                    "Two impoverished figures walking through snowstorm past stained-glass church window."
                )
                "Six" -> Quadruple(
                    "Generous patronage, fair distribution, balanced prosperity, giving and receiving.",
                    "Strings-attached gifts, one-sided power dynamics, financial inequality.",
                    "Share your knowledge and resources freely. Generosity returns tenfold in value.",
                    "Merchant weighing gold on scales and sharing coins with grateful supplicants."
                )
                "Seven" -> Quadruple(
                    "Long-term patience, assessing crop growth, evaluating investment return, deliberate wait.",
                    "Impatient frustration, giving up before harvest, unprofitable endeavors.",
                    "Pause and assess your harvest. Growth is occurring underground even when unseen.",
                    "Farmer resting on hoe contemplating lush vine bearing seven heavy golden coins."
                )
                "Eight" -> Quadruple(
                    "Dedicated apprenticeship, repetitive mastery, honing technical skill, meticulous craft.",
                    "Rushed sloppy work, lack of pride in detail, perfectionist burnout.",
                    "Focus deeply on repetitive craftsmanship. Every line of clean code sharpens your mastery.",
                    "Artisan sitting at workbench meticulously engraving eighth golden pentacle with seven finished on wall."
                )
                "Nine" -> Quadruple(
                    "Refined self-reliance, laboratory independence, luxury of solitude, fruitful abundance.",
                    "Financial overdependence, superficial pretension, neglected inner peace.",
                    "Enjoy the rich fruits of your independent labor. Cultivate your private laboratory garden.",
                    "Elegant woman in grapevine-patterned robe holding hooded falcon in lush vineyard."
                )
                "Ten" -> Quadruple(
                    "Enduring legacy, generational foundation, institutional wealth, permanent structure.",
                    "Family disputes, crumbling legacy, fragile inheritance, short-term vanity.",
                    "Build frameworks and documentation that endure for years beyond your immediate sprint.",
                    "Multi-generational family gathered in castle archway with dogs beneath ten golden pentacles."
                )
                "Page" -> Quadruple(
                    "Studious ambition, practical learner, grounding ideas, eagerness to build.",
                    "Procrastination, impractical daydreams, lack of follow-through.",
                    "Study the documentation carefully. Ground your ambition with patient practice.",
                    "Student standing in fertile field holding golden coin in gentle, reverent palms."
                )
                "Knight" -> Quadruple(
                    "Methodical execution, unwavering reliability, diligent pacing, relentless work ethic.",
                    "Stagnant stubbornness, sluggish delays, getting lost in monotonous weeds.",
                    "Put your head down and execute step by step. Methodical consistency wins.",
                    "Sturdy knight on heavy plow horse holding golden coin steadily over plowed fields."
                )
                "Queen" -> Quadruple(
                    "Grounded sanctuary, pragmatic mastery, nurturing environment, sensory luxury.",
                    "Neglecting physical health, workaholic smothering, material anxiety.",
                    "Create an organized, nourishing workspace. Pragmatism and care yield stability.",
                    "Queen seated on carved stone throne surrounded by fruit vines and leaping rabbit."
                )
                "King" -> Quadruple(
                    "Material empire builder, financial governance, master of physical reality, steady titan.",
                    "Ruthless greed, material obsession, stubborn resistance to modern shifts.",
                    "Rule your material enterprise with generous wisdom and unwavering structural stability.",
                    "King seated on throne adorned with bulls holding golden coin in lush castle terrace."
                )
                else -> Quadruple("Earth energy", "Earth block", "Channel earth", "Pentacles")
            }
            else -> Quadruple("Arcana meaning", "Reversed meaning", "Advice", "Symbols")
        }
    }

    private fun generateKeywords(rank: String, suit: Suit): List<String> {
        val base = when (suit) {
            Suit.WANDS -> listOf("Passion", "Will", "Fire", "Momentum")
            Suit.CUPS -> listOf("Intuition", "Emotion", "Flow", "Heart")
            Suit.SWORDS -> listOf("Mind", "Clarity", "Truth", "Logic")
            Suit.PENTACLES -> listOf("Manifestation", "Craft", "Earth", "Wealth")
            else -> listOf("Arcana")
        }
        return (listOf(rank) + base).take(4)
    }

    data class Quadruple(
        val upright: String,
        val reversed: String,
        val advice: String,
        val symbols: String
    )
}
