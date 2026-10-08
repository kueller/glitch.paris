package com.glitch.rest.api

import com.glitch.rest.api.director.directorApiRouting
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

val apiRouting: RoutingHttpHandler = routes(

    "/director" bind directorApiRouting,

)
