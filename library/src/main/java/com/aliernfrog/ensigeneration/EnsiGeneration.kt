package com.aliernfrog.ensigeneration

class EnsiGeneration(ensiConfig: EnsiConfig) {
    private val config = ensiConfig
    private val punctuations = setOf(".",".",".",".",".",".",".",".","..","...","!","!!","?","??","?!","!?")

    /**
     * Generates a [String] of sentences
     * @param generationType: [EnsiGenerationType] to be used
     * @param types: A [Set] of sentence type strings which will be randomized on generation
     * @param sentenceCount: Count of sentences to be generated
     * @param addStartingSentence: Adds a starting phrase at the beginning of first sentence
     * @param questionsAllowed: Randomly adds question-type sentences
     * @param punctuationsAllowed: Adds punctuations
     */
    fun generate(
        generationType: String = EnsiGenerationType.RAW,
        types: Set<String> = config.normalTypes,
        sentenceCount: Int = (1..5).random(),
        addStartingSentence: Boolean = (1..3).random() == 1,
        questionsAllowed: Boolean = true,
        punctuationsAllowed: Boolean = generationType == EnsiGenerationType.LEGIT
    ): String {
        val sentences: MutableList<String> = ArrayList()
        for (i in 0..sentenceCount) {
            val isQuestion = questionsAllowed && (1..10).random() == 1
            val type = if (isQuestion) config.questionTypes.random() else types.random()
            val addSubSentence = !isQuestion && (1..10).random() == 1
            var sentence = ""
            if (i == 0 && addStartingSentence) sentence += replacePlaceholders(config.startingTypes.random())
            sentence += replacePlaceholders(type)
            if (addSubSentence) sentence += replacePlaceholders("${setOf(",","").random()} %CONC% ${types.random()}")
            if (punctuationsAllowed && !isQuestion) sentence += punctuations.random()
            sentences.add(manageCaps(sentence, generationType))
        }
        return sentences.joinToString(" ")
    }

    /**
     * Replaces all %PLACEHOLDER%s with corresponding strings
     * @param string [String] which contains placeholders to be replaced
     */
    private fun replacePlaceholders(string: String): String {
        return string
            .replaceEach("%TIME%") { config.times.random() }
            //TODO .replaceEach("%CHARS%") { "" }
            //TODO .replaceEach("%PLACE%") { "" }
            .replaceEach("%CONC%") { config.concs.random() }
            .replaceEach("%EMOTION%") { config.emotions.random() }
            .replaceEach("%OTHER%") { config.others.random() }
            .replaceEach("%WORD%") { config.words.random() }
            .replaceEach("%VERB%") { config.verbs.random() }
            .replaceEach("%EDVERB%") { config.edVerbs.random() }
            .replaceEach("%INGVERB%") { config.ingVerbs.random() }
            //TODO .replaceEach("%WORD_COUNTED%") { "" }
            .replaceEach("%WORD_VERB%") { setOf(config.words,config.verbs).random().random() }
            //TODO .replaceEach("%LOCATION%") { "" }
    }

    /**
     * Manages uppercase-lowercase state of [string] based on [generationType]
     */
    private fun manageCaps(string: String, generationType: String): String {
        return when(generationType) {
            EnsiGenerationType.LEGIT -> string.elementAt(0).uppercase()+string.slice(IntRange(0,1))
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
    val occurences = this.count { it.toString() == oldString }
    for (i in 0..occurences) this.replaceFirst(oldString, replaceWith())
    return this
}