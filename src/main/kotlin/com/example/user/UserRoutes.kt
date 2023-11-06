package com.example.user

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRoutes() {
    val service: UserService by inject()

    route("/users") {
        get("{id}") {
            val id = requireNotNull(call.parameters["id"])
            call.respond(service.getUser(id))
        }
    }

}
