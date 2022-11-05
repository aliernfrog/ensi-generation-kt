package com.aliernfrog.ensigeneration

/**
 * Ensi generation types
 */
object EnsiGenerationType {
    /**
     * no changes are made
     */
    const val RAW = "RAW"

    /**
     * Writing rules are respected.
     */
    const val LEGIT = "LEGIT"

    /**
     * SELF EXPLANATORY?
     */
    const val ALLCAPS = "ALLCAPS"
}

/**
 * Default values for [EnsiConfig]
 */
object EnsiConfigDefaults {
    val words = setOf("me","you","we","they","alierns","indinibee","bees","momes","frogs","mouse","chicken","furries","frog","Exi's basement","free candies","ensi","van","laptop","marchmilos","mouse")
    val verbs = setOf("sobbed","adsed","feed","featured","faced","undefined","petted","mousing")
    val times = setOf("one day","weeks ago","yesterday","years ago","tomorrow")
    val chars = setOf("frog","mouse","Ensi","Aliern","Exi","Infini","marchmilo","cat","memer","karen","manager","bro","man","you","he","she","it","they","that")
    val places = setOf("hospital","basement","hideout","hotel","parking","shop","chinatina")
    val concs = setOf("and","but","when","even though","before","after","while","when")
    val emotions = setOf("what the fuck","fuck","shit","woow","wow","oh god","omg","omfg")
    val others = setOf("unfortunately","fortunately","luckily","finally","thankfully","at least","weirdly","in fact","actually","fr","for real")
    val positions = setOf("in %","at %","on %","behind %","under %","between % and %")
    // TYPES
    val normalTypes = setOf("%WORD_VERB%","%WORD_COUNTED%","%CHARS% was %WORD_VERB%","%CHARS% %EDVERB%","%VERB% %CHARS%")
    val questionTypes = setOf("was %CHARS% %VERB%?","was %CHARS% %INGVERB% %CHARS%?","was %CHARS% %INGVERB% %WORD_COUNTED%?","was %CHARS% %WORD_COUNTED%?")
    val startingTypes = setOf("%TIME%, ","%TIME% %PLACE%, ","%PLACE%, ")
}