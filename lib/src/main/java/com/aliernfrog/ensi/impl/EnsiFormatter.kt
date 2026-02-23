package com.aliernfrog.ensi.impl

fun interface EnsiFormatter {
    fun format(input: String): String

    fun toList() = listOf(this)
}

object DefaultFormatters {
    val raw = EnsiFormatter { input -> input }

    val upperCase = EnsiFormatter { it.uppercase() }

    val lowerCase = EnsiFormatter { it.lowercase() }

    val capitalized = EnsiFormatter {
        it.replaceFirstChar { char -> char.uppercase() }
    }

    fun asList() = listOf(
        raw, upperCase, lowerCase, capitalized
    )
}