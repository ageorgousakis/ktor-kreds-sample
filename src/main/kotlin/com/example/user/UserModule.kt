package com.example.user

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun userModule() = module {
    singleOf(::UserService)
}
