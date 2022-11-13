package com.aliernfrog.ensigeneration

data class EnsiConfig(
    var words: Set<String> = EnsiConfigDefaults.words,
    var verbs: Set<String> = EnsiConfigDefaults.verbs,
    var edVerbs: Set<String> = verbs.filter { it.lowercase().endsWith("ed") }.toSet().ifEmpty { verbs },
    var ingVerbs: Set<String> = verbs.filter { it.lowercase().endsWith("ing") }.toSet().ifEmpty { verbs },
    val times: Set<String> = EnsiConfigDefaults.times,
    val chars: Set<String> = EnsiConfigDefaults.chars,
    val places: Set<String> = EnsiConfigDefaults.places,
    val concs: Set<String> = EnsiConfigDefaults.concs,
    val emotions: Set<String> = EnsiConfigDefaults.emotions,
    val others: Set<String> = EnsiConfigDefaults.others,
    val positions: Set<String> = EnsiConfigDefaults.positions,
    // TYPES
    val normalTypes: Set<String> = EnsiConfigDefaults.normalTypes,
    val questionTypes: Set<String> = EnsiConfigDefaults.questionTypes,
    val startingTypes: Set<String> = EnsiConfigDefaults.startingTypes,
    // GENERATION OPTIONS
    val sentenceCountRange: Range = EnsiConfigDefaults.sentenceCountRange,
    val wordAsCharAllowed: Boolean = EnsiConfigDefaults.wordAsCharAllowed,
    val startingSentenceAllowed: Boolean = EnsiConfigDefaults.startingSentenceAllowed,
    val questionsAllowed: Boolean = EnsiConfigDefaults.questionsAllowed,
    val punctuationsAllowed: Boolean = EnsiConfigDefaults.punctuationsAllowed,
    val subSentencesAllowed: Boolean = EnsiConfigDefaults.subSentencesAllowed
)

data class Range(
    val min: Int,
    val max: Int
)