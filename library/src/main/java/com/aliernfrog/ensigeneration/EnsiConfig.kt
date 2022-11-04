package com.aliernfrog.ensigeneration

data class EnsiConfig(
    var words: Set<String> = setOf("me","you","we","they","alierns","indinibee","bees","momes","frogs","mouse","chicken","furries","frog","Exi's basement","free candies","ensi","van","laptop","marchmilos","mouse"),
    var verbs: Set<String> = setOf("sobbed","adsed","feed","featured","faced","undefined","petted","mousing"),
    var edVerbs: Set<String> = verbs.filter { it.lowercase().endsWith("ed") }.toSet(),
    var ingVerbs: Set<String> = verbs.filter { it.lowercase().endsWith("ing") }.toSet(),
    val times: Set<String> = setOf("one day","weeks ago","yesterday","years ago","tomorrow"),
    val chars: Set<String> = setOf("frog","mouse","Ensi","Aliern","Exi","Infini","marchmilo","cat","memer","karen","manager","bro","man","you","he","she","it","they","that"),
    val places: Set<String> = setOf("hospital","basement","hideout","hotel","parking","shop","chinatina"),
    val concs: Set<String> = setOf("and","but","when","even though","before","after","while","when"),
    val emotions: Set<String> = setOf("what the fuck","fuck","shit","woow","wow","oh god","omg","omfg"),
    val others: Set<String> = setOf("unfortunately","fortunately","luckily","finally","thankfully","at least","weirdly","in fact","actually","fr","for real"),
    val positions: Set<String> = setOf("in %","at %","on %","behind %","under %","between % and %"),
    // TYPES
    val normalTypes: Set<String> = setOf("%WORD_VERB%","%WORD_COUNTED%","%CHARS% was %WORD_VERB%","%CHARS% %EDVERB%","%VERB% %CHARS%"),
    val questionTypes: Set<String> = setOf("was %CHARS% %VERB%?","was %CHARS% %INGVERB% %CHARS%?","was %CHARS% %INGVERB% %WORD_COUNTED%?","was %CHARS% %WORD_COUNTED%?"),
    val startingTypes: Set<String> = setOf("%TIME%, ","%TIME% %PLACE%, ","%PLACE%, ")
)