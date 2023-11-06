package com.example

import com.example.cache.cacheModule
import com.example.serializer.jsonSerializer
import com.example.user.userModule
import com.example.user.userRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main() {
    val config = loadConfig<Config>("/application.yaml")
    embeddedServer(
        Netty, host = config.http.host, port = config.http.port
    ) {
        configure(config)
    }.start(wait = true)
}

fun Application.configure(config: Config) {
    install(ContentNegotiation) {
        json(jsonSerializer)
    }
    install(Koin) {
        slf4jLogger()
        modules(
            cacheModule(config.cache), userModule()
        )
    }
    routing {
        userRoutes()
    }
}

