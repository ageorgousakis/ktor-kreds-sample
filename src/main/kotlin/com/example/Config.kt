package com.example

import com.example.cache.CacheConfig
import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.addResourceSource


data class Config(
    val http: HttpConfig = HttpConfig(),
    val cache: CacheConfig = CacheConfig(),
)

data class HttpConfig(
    val host: String = "localhost",
    val port: Int = 8080
)

inline fun <reified T : Any> loadConfig(configFile: String): T =
    ConfigLoaderBuilder.default()
        .addResourceSource(configFile)
        .build().loadConfigOrThrow<T>()



