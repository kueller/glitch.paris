package com.glitch.rest.api

import com.glitch.rest.api.director.directorApiRouting
import com.glitch.rest.filter.genericExceptionFilter
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

val apiRouting: RoutingHttpHandler = routes(

    "/director" bind directorApiRouting,

).withFilter(genericExceptionFilter)
