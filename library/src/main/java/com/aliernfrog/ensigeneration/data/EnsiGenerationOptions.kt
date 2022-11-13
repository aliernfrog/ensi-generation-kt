package com.aliernfrog.ensigeneration.data

import com.aliernfrog.ensigeneration.EnsiGenerationType

/**
 * Options for generating sentences
 * @param types: A [Set] of sentence type strings which will be randomized on generation
 * @param generationType: [EnsiGenerationType] to be used
 * @param sentenceCount: Count of sentences to be generated
 * @param addStartingSentence: Adds a starting phrase at the beginning of first sentence
 * @param wordAsChar: If words can be used as characters
 * @param questionsAllowed: Randomly adds question-type sentences
 * @param punctuationsAllowed: Adds punctuations
 * @param subSentencesAllowed: Randomly adds a second sentence after sentences
 */
data class EnsiGenerationOptions(
    val types: Set<String>,
    val generationType: String = EnsiGenerationType.RAW,
    val sentenceCount: Int = (1..5).random(),
    val addStartingSentence: Boolean = (1..3).random() == 1,
    val wordAsChar: Boolean = false,
    val questionsAllowed: Boolean = true,
    val punctuationsAllowed: Boolean = generationType == EnsiGenerationType.LEGIT,
    val subSentencesAllowed: Boolean = true
)
