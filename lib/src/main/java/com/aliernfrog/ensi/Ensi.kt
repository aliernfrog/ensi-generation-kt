package com.aliernfrog.ensi

import com.aliernfrog.ensi.data.EnsiGenerationOptions
import com.aliernfrog.ensi.data.EnsiWordType
import com.aliernfrog.ensi.impl.DefaultFormatters
import com.aliernfrog.ensi.impl.EnsiFormatter
import com.aliernfrog.ensi.impl.EnsiInputData
import com.aliernfrog.ensi.impl.PredefinedResponses
import kotlin.random.Random

@Suppress("unused")
class Ensi(
    types: List<EnsiWordType>,
    private val schemes: List<String>,
    private val formatters: List<EnsiFormatter> = DefaultFormatters.asList()
) {
    val defaultGenerationOptions = EnsiGenerationOptions(
        schemes = schemes,
        formatters = formatters
    )

    fun generate(
        message: String? = null,
        options: EnsiGenerationOptions = defaultGenerationOptions
    ): String {
        val input = EnsiInputData(message ?: "")
        val formatter = getRandomFormatter()
        var response = "You said: $message"
        val hasPredefinedResponse = PredefinedResponses.entries.any {
            it.response.generateResponseFor(input, this)?.let { generated ->
                response = generated
                true
            } == true
        }
        if (!hasPredefinedResponse) {
            1+1
            /* TODO */
        }
        return formatter.format(response)
    }

    private fun getRandomFormatter(): EnsiFormatter {
        if (Random.nextFloat() < 0.7f) return DefaultFormatters.raw
        return formatters.randomOrNull() ?: DefaultFormatters.raw
    }
}

