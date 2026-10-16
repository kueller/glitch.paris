package com.glitch.rest

import com.glitch.rest.api.apiRouting
import com.glitch.rest.web.webRouting
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

/**
 * - [webRouting] is for anything displayed on a browser. This includes
 * HTML rendered pages but also static files that can be opened directly.
 * - [apiRouting] is for all non-displaying endpoints. Mostly handling
 * JSON.
 */
val restRouting: RoutingHttpHandler = routes(

    webRouting,

    "/api" bind apiRouting

)
