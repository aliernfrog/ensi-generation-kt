package com.aliernfrog.ensi.impl

class EnsiInputData(
    val raw: String
) {
    val lowercase = raw.lowercase()

    val args = raw.split(" ")

    val argsLowercase = lowercase.split(" ")
}