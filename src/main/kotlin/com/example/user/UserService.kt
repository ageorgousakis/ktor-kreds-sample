package com.example.user

import com.example.cache.CacheManager
import com.example.cache.getOrElse
import com.example.cache.put
import kotlin.time.Duration.Companion.days


class UserService(
    private val cacheManager: CacheManager
) {

    suspend fun getUser(id: String) =
        cacheManager.getOrElse("user:$id") {
            User(id, "User #$id").also {
                cacheManager.put("user:$id", it, 1.days)
            }
        }
}
