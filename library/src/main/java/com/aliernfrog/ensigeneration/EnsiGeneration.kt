package com.aliernfrog.ensigeneration

import java.util.*
import kotlin.collections.ArrayList

class EnsiGeneration(ensiConfig: EnsiConfig) {
    private val config = ensiConfig
    private val punctuations = setOf(".",".",".",".",".",".",".",".","..","...","!","!!","?","??","?!","!?")

    /**
     * Generates a [String] of sentences
     * @param generationType: [EnsiGenerationType] to be used
     * @param types: A [Set] of sentence type strings which will be randomized on generation
     * @param sentenceCount: Count of sentences to be generated
     * @param addStartingSentence: Adds a starting phrase at the beginning of first sentence
     * @param wordAsChar: If words can be used as characters
     * @param questionsAllowed: Randomly adds question-type sentences
     * @param punctuationsAllowed: Adds punctuations
     */
    fun generate(
        generationType: String = EnsiGenerationType.RAW,
        types: Set<String> = config.normalTypes,
        sentenceCount: Int = (1..5).random(),
        addStartingSentence: Boolean = (1..3).random() == 1,
        wordAsChar: Boolean = false,
        questionsAllowed: Boolean = true,
        punctuationsAllowed: Boolean = generationType == EnsiGenerationType.LEGIT
    ): String {
        val sentences: MutableList<String> = ArrayList()
        for (i in 0..sentenceCount) {
            val isQuestion = questionsAllowed && (1..10).random() == 1
            val type = if (isQuestion) config.questionTypes.random() else types.random()
            val addSubSentence = !isQuestion && (1..10).random() == 1
            var sentence = ""
            if (i == 0 && addStartingSentence) sentence += replacePlaceholders(config.startingTypes.random(), wordAsChar)
            sentence += replacePlaceholders(type, wordAsChar)
            if (addSubSentence) sentence += replacePlaceholders("${setOf(",","").random()} %CONC% ${types.random()}", wordAsChar)
            if (punctuationsAllowed && !isQuestion) sentence += punctuations.random()
            sentences.add(manageCaps(sentence, generationType))
        }
        return sentences.joinToString(" ")
    }

    /**
     * Replaces all %PLACEHOLDER%s with corresponding strings
     * @param string [String] which contains placeholders to be replaced
     */
    private fun replacePlaceholders(string: String, wordAsChar: Boolean = false): String {
        return string
            .replaceEach("%TIME%") { config.times.random() }
            .replaceEach("%CHARS%") { getChars(wordAsChar) }
            .replaceEach("%PLACE%") { getLocation() }
            .replaceEach("%CONC%") { config.concs.random() }
            .replaceEach("%EMOTION%") { config.emotions.random() }
            .replaceEach("%OTHER%") { config.others.random() }
            .replaceEach("%WORD%") { config.words.random() }
            .replaceEach("%VERB%") { config.verbs.random() }
            .replaceEach("%EDVERB%") { config.edVerbs.random() }
            .replaceEach("%INGVERB%") { config.ingVerbs.random() }
            .replaceEach("%WORD_COUNTED%") { getWords() }
            .replaceEach("%WORD_VERB%") { setOf(config.words,config.verbs).random().random() }
    }

    private fun getChars(wordAsChar: Boolean): String {
        val chars: MutableList<String> = ArrayList()
        val charCount = (1..3).random()
        for (i in 0..charCount) chars.add(
            if (wordAsChar) setOf(config.chars,config.words).random().random()
            else config.chars.random()
        )
        return pluralString(chars)
    }

    private fun getWords(): String {
        val words: MutableList<String> = ArrayList()
        val wordCount = (1..3).random()
        for (i in 0..wordCount) words.add(config.words.random())
        return pluralString(words)
    }

    private fun getLocation(disableOwner: Boolean = false): String {
        val owner = !disableOwner && (1..10).random() == 1
        var finalString = setOf(config.chars,config.places,config.words).random().random()
        if (owner) finalString += "'s ${getLocation(true)}"
        return config.positions.random().replaceEach("%") { config.places.random() }
    }

    private fun pluralString(strings: MutableList<String>): String {
        return when(strings.size) {
            1 -> strings[0]
            2 -> "${strings[0]} and ${strings[1]}"
            else -> strings.joinToString(", ")
        }
    }

    /**
     * Manages uppercase-lowercase state of [string] based on [generationType]
     */
    private fun manageCaps(string: String, generationType: String): String {
        return when(generationType) {
            EnsiGenerationType.LEGIT -> string.replaceFirstChar { if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()) else it.toString() }
            EnsiGenerationType.ALLCAPS -> string.uppercase()
            else -> string
        }
    }
}

/**
 * Replaces each occurence of [oldString] with [replaceWith]
 * @param oldString old [String] to replace
 * @param replaceWith [String] [Unit] to replace [oldString]
 */
private fun String.replaceEach(oldString: String, replaceWith: () -> String): String {
    var finalString = this
    val occurences = finalString.count { it.toString() == oldString }
    for (i in 0..occurences) {
        finalString = finalString.replaceFirst(oldString, replaceWith())
    }
    return finalString
}