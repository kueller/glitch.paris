package com.glitch.rest

import com.glitch.config.GlitchMoshi
import com.glitch.config.GlitchMoshi.auto
import com.glitch.exception.ParsingError
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.Response

val json = GlitchMoshi

inline fun <reified T : Any> incomingJson(request: Request): T {
    val requestLens = Body.auto<T>().toLens()

    try {
        return requestLens.extract(request)
    } catch (e: Exception) {
        throw ParsingError(e.message ?: e.toString())
    }
}

inline fun <reified T : Any> outgoingJson(schema: T) = Body.auto<T>().toLens().of<Response>(schema)
