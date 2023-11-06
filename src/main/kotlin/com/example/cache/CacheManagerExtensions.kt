package com.example.cache

import kotlin.time.Duration

suspend inline fun <reified T> CacheManager.get(key: String): T = getKey(key, T::class.java)
suspend inline fun <reified T> CacheManager.getOrNull(key: String, orElse: () -> T? = { null }): T? = if (exists(key)) getKeyOrNull(key, T::class.java) else orElse()
suspend inline fun <reified T> CacheManager.getOrElse(key: String, orElse: () -> T): T = if (exists(key)) getKey(key, T::class.java) else orElse()
suspend inline fun <reified T> CacheManager.getList(key: String): List<T> = getListFromKey(key, T::class.java)
suspend inline fun <reified T> CacheManager.getListOrElse(key: String, ifEmpty: () -> List<T> = { emptyList() }): List<T> = if (exists(key)) getListFromKey(key, T::class.java) else ifEmpty()
suspend inline fun <reified T> CacheManager.getMap(key: String): Map<String, T> = getMapFromKey(key, T::class.java)
suspend inline fun <reified T> CacheManager.getMapOrElse(key: String, ifEmpty: () -> Map<String, T> = { emptyMap() }): Map<String, T> = if (exists(key)) getMapFromKey(key, T::class.java) else ifEmpty()
suspend inline fun <reified T> CacheManager.getFromMap(key: String, mapKey: String, orElse: () -> T? = { null }): T? = getMapFromKey(key, mapKey, T::class.java)
    ?: orElse()

suspend inline fun <reified T> CacheManager.put(key: String, value: T) = putKey(key, value, T::class.java)
suspend inline fun <reified T> CacheManager.put(key: String, value: T, ttl: Duration) = putKey(key, value, ttl, T::class.java)
suspend inline fun <reified T> CacheManager.putList(key: String, value: List<T>) = putListToKey(key, value, T::class.java)
suspend inline fun <reified T> CacheManager.putList(key: String, value: List<T>, ttl: Duration) = putListToKey(key, value, ttl, T::class.java)
suspend inline fun <reified T> CacheManager.putMap(key: String, value: Map<String, T>) = putMapToKey(key, value, T::class.java)
suspend inline fun <reified T> CacheManager.putMap(key: String, value: Map<String, T>, ttl: Duration) = putMapToKey(key, value, ttl, T::class.java)

suspend inline fun <reified T : Any> CacheManager.find(pattern: String, ifEmpty: () -> List<T> = { emptyList() }): List<T> = find(pattern, T::class.java).ifEmpty { ifEmpty() }

suspend inline fun <reified T> CacheManager.addToList(key: String, vararg values: T): List<T> = addToList(key, T::class.java, *values)

suspend inline fun <reified T> CacheManager.addToMap(key: String, additionalMap: Map<String, T>): Map<String, T> = addToMap(key, T::class.java, additionalMap)

suspend inline fun <reified T> CacheManager.deleteFromList(key: String, vararg values: T): List<T> = deleteFromList(key, T::class.java, *values)

suspend inline fun <reified T> CacheManager.deleteFromMap(key: String, removalMap: Map<String, T>): Map<String, T> = deleteFromMap(key, T::class.java, removalMap)

