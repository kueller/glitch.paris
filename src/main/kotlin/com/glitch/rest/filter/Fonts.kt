package com.glitch.rest.filter

import com.glitch.config.Environments
import com.glitch.config.appConfig
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

internal val extensions = Regex("\\.(woff|woff2|eot)$")


val fontAccessFilter: Filter = { next: HttpHandler ->
    { request: Request ->
        val match: Boolean = extensions.containsMatchIn(request.uri.path)

        when (appConfig.env) {
            Environments.PROD if match -> {
                val origin: String? = request.header("Origin")
                val fetchSite: String? = request.header("Sec-Fetch-Site")

                if ((origin == null && fetchSite == null)
                    || (origin != null && origin != appConfig.selfUrl)
                    || (fetchSite != null && fetchSite !in listOf("same-site", "same-origin"))
                ) {
                    Response.Companion(Status.FORBIDDEN)
                } else {
                    next(request)
                }
            }

            else -> next(request)
        }
    }
}