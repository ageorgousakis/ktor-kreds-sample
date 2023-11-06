package com.example.cache

import kotlin.time.Duration

interface CacheManager {

    // -----------------------------------------------------------------------------------//
    //  Methods that must be implemented.                                                 //
    // -----------------------------------------------------------------------------------//

    val serializer: Serializer? get() = null

    suspend fun getRaw(key: String): ByteArray?
    suspend fun putRaw(key: String, value: ByteArray, ttl: Duration? = null)
    suspend fun findKeys(pattern: String): List<String>
    suspend fun delete(key: String)

    // -------------------------------------------------------------------------------------//
    //  Methods below have inefficient default implementation and should be overridden      //
    //  for a better performance. This is one of main reasons why these methods are         //
    //  not extension functions.                                                            //
    // -------------------------------------------------------------------------------------//

    suspend fun <T> getKey(key: String, clazz: Class<T>): T {
        return requireNotNull(getRaw(key)).let { serializer?.deserialize(it, clazz) ?: clazz.cast(it) }
    }

    suspend fun <T> getKeyOrNull(key: String, clazz: Class<T>): T? {
        return getRaw(key)?.let { serializer?.deserialize(it, clazz) }
    }

    suspend fun <T> getListFromKey(key: String, elementClass: Class<T>): List<T> {
        return getRaw(key)?.let { serializer?.deserializeList(it, elementClass) } ?: emptyList()
    }

    suspend fun <T> getMapFromKey(key: String, valueClass: Class<T>): Map<String, T> {
        return getRaw(key)?.let { serializer?.deserializeMap(it, String::class.java, valueClass) } ?: emptyMap()
    }

    suspend fun <T> getMapFromKey(key: String, mapKey: String, clazz: Class<T>): T? {
        return getMapFromKey(key, clazz)[mapKey]
    }

    suspend fun <T> putKey(key: String, value: T, clazz: Class<T>) {
        serializer?.serialize(value, clazz)?.let { ser -> putRaw(key, ser) }
    }

    suspend fun <T> putKey(key: String, value: T, ttl: Duration, clazz: Class<T>) {
        serializer?.serialize(value, clazz)?.let { ser -> putRaw(key, ser, ttl) }
    }

    suspend fun <T> putListToKey(key: String, list: List<T>, clazz: Class<T>) {
        serializer?.serializeList(list, clazz)?.let { ser -> putRaw(key, ser) }
    }

    suspend fun <T> putListToKey(key: String, list: List<T>, ttl: Duration, clazz: Class<T>) {
        serializer?.serializeList(list, clazz)?.let { ser -> putRaw(key, ser, ttl) }
    }

    suspend fun <T> putMapToKey(key: String, map: Map<String, T>, clazz: Class<T>) {
        serializer?.serializeMap(map, String::class.java, clazz)?.let { ser -> putRaw(key, ser) }
    }

    suspend fun <T> putMapToKey(key: String, map: Map<String, T>, ttl: Duration, clazz: Class<T>) {
        serializer?.serializeMap(map, String::class.java, clazz)?.let { ser -> putRaw(key, ser, ttl) }
    }

    suspend fun <T> addToList(key: String, clazz: Class<T>, vararg values: T): List<T> {
        val list = getListFromKey(key, clazz).toMutableList()
        list += values.toList()
        putListToKey(key, list, clazz)
        return list
    }

    suspend fun <T> addToMap(key: String, clazz: Class<T>, additionalMap: Map<String, T>): Map<String, T> {
        val map = getMapFromKey(key, clazz).toMutableMap()
        map += additionalMap
        putMapToKey(key, map, clazz)
        return map
    }

    suspend fun findAllKeys(): List<String> {
        return findKeys("*")
    }

    suspend fun <T : Any> find(pattern: String, elementClass: Class<T>): List<T> {
        return findKeys(pattern).mapNotNull { getKeyOrNull(it, elementClass) }
    }

    suspend fun delete(keys: Iterable<String>) {
        keys.forEach { delete(it) }
    }

    suspend fun <T> deleteFromList(key: String, clazz: Class<T>, vararg values: T): List<T> {
        val list = getListFromKey(key, clazz).toMutableList()
        list -= values.toList()
        putListToKey(key, list, clazz)
        return list
    }

    suspend fun <T> deleteFromMap(key: String, clazz: Class<T>, removalMap: Map<String, T>): Map<String, T> {
        val map = getMapFromKey(key, clazz).toMutableMap()
        map -= removalMap.keys
        putMapToKey(key, map, clazz)
        return map
    }

    suspend fun deleteAll() {
        findAllKeys().forEach { delete(it) }
    }

    suspend fun exists(key: String): Boolean {
        return getRaw(key) != null
    }

    suspend fun expire(key: String, ttl: Duration) {
//        if (ttlMs > 0) {
//            GlobalScope.launch {
//                delay(ttl)
//                delete(key)
//            }
//        }
    }
}
