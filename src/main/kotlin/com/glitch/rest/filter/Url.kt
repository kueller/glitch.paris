package com.glitch.rest.filter

import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status

/**
 * For paths that are not the root directory, redirect all URLs with
 * trailing slashes/ to their counterparts without the slash.
 */
val trailingSlashFilter: Filter = { next: HttpHandler ->
    { request: Request ->
        when (request.uri.path.last()) {
            '/' if (request.uri.path != "/") -> {
                val newUri = request.uri.path(request.uri.path.removeSuffix("/"))
                    .query(request.uri.query)

                Response.Companion(Status.PERMANENT_REDIRECT)
                    .header("Location", newUri.toString())
            }

            else -> next(request)
        }
    }
}