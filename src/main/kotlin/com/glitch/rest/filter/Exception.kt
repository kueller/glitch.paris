package com.glitch.rest.filter

import com.glitch.exception.GlitchException
import org.http4k.core.*
import org.http4k.lens.contentType

val genericExceptionFilter: Filter = { next: HttpHandler ->
    { request: Request ->
        try {
            next(request)
        } catch (error: GlitchException) {
            Response(error.status).body(error.message).contentType(ContentType.TEXT_PLAIN)
        } catch (error: Exception) {
            println(error.message ?: error.toString())
            Response(Status.INTERNAL_SERVER_ERROR).body("Internal server error.").contentType(ContentType.TEXT_PLAIN)
        }
    }
}
