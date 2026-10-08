package com.glitch.rest.web.shows

import com.glitch.rest.web.HTMLSubPage
import com.glitch.rest.filter.adminOnly
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.bind
import org.http4k.core.Method.GET

val showsRouting: RoutingHttpHandler = routes(

    "/" bind GET to HTMLSubPage(),

    "/manage" bind GET to HTMLSubPage().withFilter(adminOnly),

)