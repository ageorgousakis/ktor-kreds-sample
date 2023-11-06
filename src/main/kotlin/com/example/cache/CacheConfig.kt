package com.example.cache

import io.github.crackthecodeabhi.kreds.connection.KredsClientConfig

data class CacheConfig(
    val endpoint: String = "localhost:6379",
)

val defaultClientConfig = KredsClientConfig.Builder(readTimeoutSeconds = 30).build()
