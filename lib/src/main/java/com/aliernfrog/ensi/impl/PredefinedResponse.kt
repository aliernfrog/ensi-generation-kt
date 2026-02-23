package com.aliernfrog.ensi.impl

import com.aliernfrog.ensi.Ensi

fun interface PredefinedResponse {
    fun generateResponseFor(input: EnsiInputData, ensi: Ensi): String?
}

enum class PredefinedResponses(val response: PredefinedResponse) {
    HELLO(
        PredefinedResponse { input, _ ->
            if (
                input.argsLowercase.contains("hi")
                || input.argsLowercase.contains("hello")
            ) "wow hi bro"
            else null
        }
    ),

    GOOD_NIGHT(
        PredefinedResponse { input, _ ->
            if (
                input.argsLowercase.contains("gn")
                || input.lowercase.contains("good night")
            ) "gn my,"
            else null
        }
    ),

    GIVE_NICK(
        PredefinedResponse { _, _ ->
            //if (input.argsLowercase.containsAll("give", "nick")) TODO
            null
        }
    ),

    TELL_STORY(
        PredefinedResponse { _, _ ->
            //if (input.argsLowercase.containsAll("tell", "story" or "stories")) TODO
            null
        }
    )
}