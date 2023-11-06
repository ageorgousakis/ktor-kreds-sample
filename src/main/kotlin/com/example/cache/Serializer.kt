package com.example.cache


interface Serializer {
    fun <T> serialize(obj: T, objClass: Class<T>): ByteArray
    fun <T> serializeList(list: List<T>, elementClass: Class<T>): ByteArray
    fun <K, V> serializeMap(map: Map<K, V>, keyClass: Class<K>, valueClass: Class<V>): ByteArray
    fun <T> deserialize(byteArray: ByteArray, toClass: Class<T>): T
    fun <T> deserializeList(byteArray: ByteArray, elementClass: Class<T>): List<T>
    fun <K, V> deserializeMap(byteArray: ByteArray, keyClass: Class<K>, valueClass: Class<V>): Map<K, V>
}
