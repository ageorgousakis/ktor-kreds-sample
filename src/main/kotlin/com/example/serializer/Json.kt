package com.example.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val jsonSerializer = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
    explicitNulls = false
//    serializersModule = jsonSerializersModule
}
