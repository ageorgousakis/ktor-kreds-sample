package com.example.cache

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class KotlinxSerializer(
    private val json: Json
) : Serializer {

    @Suppress("UNCHECKED_CAST")
    override fun <T> serialize(obj: T, objClass: Class<T>): ByteArray {
        val serializer = json.serializersModule.serializer(objClass) as KSerializer<T>
        return json.encodeToString(serializer, obj).toByteArray()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> serializeList(list: List<T>, elementClass: Class<T>): ByteArray {
        val serializer = ListSerializer(json.serializersModule.serializer(elementClass) as KSerializer<T>)
        return json.encodeToString(serializer, list).toByteArray()
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K, V> serializeMap(map: Map<K, V>, keyClass: Class<K>, valueClass: Class<V>): ByteArray {
        val serializer = MapSerializer(
            json.serializersModule.serializer(keyClass) as KSerializer<K>,
            json.serializersModule.serializer(valueClass) as KSerializer<V>)
        return json.encodeToString(serializer, map).toByteArray()
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T> deserialize(byteArray: ByteArray, toClass: Class<T>): T {
        val deserializer = json.serializersModule.serializer(toClass) as KSerializer<T>
        return json.decodeFromString(deserializer, String(byteArray))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> deserializeList(byteArray: ByteArray, elementClass: Class<T>): List<T> {
        val deserializer = ListSerializer(json.serializersModule.serializer(elementClass) as KSerializer<T>)
        return json.decodeFromString(deserializer, String(byteArray))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K, V> deserializeMap(byteArray: ByteArray, keyClass: Class<K>, valueClass: Class<V>): Map<K, V> {
        val deserializer = MapSerializer(
            json.serializersModule.serializer(keyClass) as KSerializer<K>,
            json.serializersModule.serializer(valueClass) as KSerializer<V>
        )
        return json.decodeFromString(deserializer, String(byteArray))
    }

}
