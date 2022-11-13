package com.aliernfrog.ensigeneration

import com.aliernfrog.ensigeneration.data.EnsiConfig
import com.aliernfrog.ensigeneration.data.EnsiGenerationOptions
import kotlin.collections.ArrayList

class EnsiGeneration(ensiConfig: EnsiConfig) {
    val config = ensiConfig
    private val punctuations = setOf(".",".",".",".",".",".",".",".","..","...","!","!!","?","??","?!","!?")

    /**
     * Generates a [String] of sentences
     * @param options: [EnsiGenerationOptions] to be used
     */
    fun generate(options: EnsiGenerationOptions = EnsiGenerationOptions(config.normalTypes)): String {
        val sentences: MutableList<String> = ArrayList()
        for (i in 0..options.sentenceCount) {
            val isQuestion = options.questionsAllowed && (1..10).random() == 1
            val type = if (isQuestion) config.questionTypes.random() else options.types.random()
            val addSubSentence = options.subSentencesAllowed && !isQuestion && (1..10).random() == 1
            var sentence = ""
            if (i == 0 && options.addStartingSentence) sentence += replacePlaceholders(config.startingTypes.random(), options.wordAsChar)
            sentence += replacePlaceholders(type, options.wordAsChar)
            if (addSubSentence) sentence += replacePlaceholders("${setOf(",","").random()} %CONC% ${options.types.random()}", options.wordAsChar)
            if (options.punctuationsAllowed && !isQuestion) sentence += punctuations.random()
            sentences.add(manageCaps(sentence, options.generationType))
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
            EnsiGenerationType.LEGIT -> string.replaceFirstChar { it.uppercase() }
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
    val occurrences = finalString.split(oldString).size - 1
    for (i in 0..occurrences) {
        finalString = finalString.replaceFirst(oldString, replaceWith())
    }
    return finalString
}