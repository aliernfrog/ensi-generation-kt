package com.aliernfrog.ensi.data

import com.aliernfrog.ensi.impl.EnsiFormatter

data class EnsiGenerationOptions(
    val schemes: List<String>,
    val formatters: List<EnsiFormatter>
)