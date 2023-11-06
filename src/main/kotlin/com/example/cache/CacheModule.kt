package com.example.cache

import com.example.serializer.jsonSerializer
import io.github.crackthecodeabhi.kreds.connection.Endpoint
import io.github.crackthecodeabhi.kreds.connection.KredsClient
import io.github.crackthecodeabhi.kreds.connection.newClient
import io.github.crackthecodeabhi.kreds.connection.shutdown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.dsl.onClose

fun cacheModule(config: CacheConfig) = module(createdAtStart = true) {
    single<KredsClient> {
        newClient(Endpoint.from(config.endpoint), defaultClientConfig)
    } onClose  {
        CoroutineScope(NonCancellable).launch {
            shutdown()
        }
    }
    single<CacheManager> {
        KredsCacheManager(
            KotlinxSerializer(jsonSerializer),
            get()
        )
    }

}
