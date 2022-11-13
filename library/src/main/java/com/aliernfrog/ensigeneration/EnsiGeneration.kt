package com.aliernfrog.ensigeneration

import kotlin.collections.ArrayList

class EnsiGeneration(ensiConfig: EnsiConfig) {
    private val config = ensiConfig
    private val punctuations = setOf(".",".",".",".",".",".",".",".","..","...","!","!!","?","??","?!","!?")

    /**
     * Generates a [String] of sentences
     * @param generationType: [EnsiGenerationType] to be used
     * @param types: A [Set] of sentence type strings which will be randomized on generation
     * @param sentenceCount: Count of sentences to be generated
     * @param wordAsCharAllowed: If words can be used as characters
     * @param startingSentenceAllowed: Randomly adds starting sentence before the first sentence
     * @param questionsAllowed: Randomly adds question-type sentences
     * @param addPunctuations: Adds punctuations
     * @param subSentencesAllowed: Randomly adds a second sentence after sentences
     */
    fun generate(
        generationType: String = EnsiGenerationType.RAW,
        types: Set<String> = config.normalTypes,
        sentenceCount: Int = (config.sentenceCountRange.min..config.sentenceCountRange.max).random(),
        wordAsCharAllowed: Boolean = config.wordAsCharAllowed,
        startingSentenceAllowed: Boolean = config.startingSentenceAllowed,
        questionsAllowed: Boolean = config.questionsAllowed,
        addPunctuations: Boolean = config.punctuationsAllowed && generationType == EnsiGenerationType.LEGIT,
        subSentencesAllowed: Boolean = true
    ): String {
        val sentences: MutableList<String> = ArrayList()
        for (i in 0..sentenceCount) {
            val isQuestion = questionsAllowed && (1..10).random() == 1
            val type = if (isQuestion) config.questionTypes.random() else types.random()
            val addStartingSentence = startingSentenceAllowed && i == 0 && (1..3).random() == 1
            val addSubSentence = subSentencesAllowed && !isQuestion && (1..10).random() == 1
            var sentence = ""
            if (i == 0 && addStartingSentence) sentence += replacePlaceholders(config.startingTypes.random(), wordAsCharAllowed)
            sentence += replacePlaceholders(type, wordAsCharAllowed)
            if (addSubSentence) sentence += replacePlaceholders("${setOf(",","").random()} %CONC% ${types.random()}", wordAsCharAllowed)
            if (addPunctuations && !isQuestion) sentence += punctuations.random()
            sentences.add(manageCaps(sentence, generationType))
        }
        return sentences.joinToString(" ")
    }

    /**
     * Replaces all %PLACEHOLDER%s with corresponding strings
     * @param string [String] which contains placeholders to be replaced
     */
    private fun replacePlaceholders(string: String, wordAsCharAllowed: Boolean = false): String {
        return string
            .replaceEach("%TIME%") { config.times.random() }
            .replaceEach("%CHARS%") { getChars(wordAsCharAllowed) }
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

    private fun getChars(wordAsCharAllowed: Boolean): String {
        val chars: MutableList<String> = ArrayList()
        val charCount = (1..3).random()
        for (i in 0..charCount) chars.add(
            if (wordAsCharAllowed) setOf(config.chars,config.words).random().random()
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
    for (i in 1..occurrences) {
        finalString = finalString.replaceFirst(oldString, replaceWith())
    }
    return finalString
}