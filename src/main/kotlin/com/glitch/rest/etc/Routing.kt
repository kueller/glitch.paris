package com.glitch.rest.etc

import com.glitch.config.TopLevelPage
import com.glitch.rest.HTMLMainPage
import com.glitch.rest.HTMLSubPage
import com.glitch.rest.adminOnly
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.bind
import org.http4k.core.Method.GET

val etcRouting: RoutingHttpHandler = routes(

    "/" bind GET to HTMLMainPage(TopLevelPage.ETC),

    "/shows" bind GET to HTMLSubPage(),

    "/shows/manage" bind GET to HTMLSubPage().withFilter(adminOnly),

)