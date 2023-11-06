package com.example.cache

import io.github.crackthecodeabhi.kreds.args.SetOption
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import kotlin.text.toByteArray
import kotlin.time.Duration

class KredsCacheManager(
    override val serializer: Serializer,
    private val client: KredsClient,
) : CacheManager {

    override suspend fun getRaw(key: String): ByteArray? =
        client.get(key)?.toByteArray()

    override suspend fun putRaw(key: String, value: ByteArray, ttl: Duration?) {
        client.set(
            key, String(value),
            if (ttl != null)
                SetOption.Builder().exSeconds(ttl.inWholeSeconds.toULong())
                    .build()
            else null
        )
    }


    override suspend fun delete(key: String) {
        client.del(key)
    }

    override suspend fun expire(key: String, ttl: Duration) {
        client.expire(key, ttl.inWholeSeconds.toULong())
    }

    override suspend fun findKeys(pattern: String): List<String> =
        client.keys(pattern)

    override suspend fun exists(key: String): Boolean =
        client.exists(key) == 1L

//    private fun getClient() =
//        newClient(Endpoint.from("127.0.0.1:6379"))

}
