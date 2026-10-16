package com.glitch.rest.web.shows

import com.glitch.config.TopLevelPage
import com.glitch.rest.web.HTMLSubPage
import com.glitch.rest.filter.adminOnly
import com.glitch.rest.web.HTMLMainPage
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.bind
import org.http4k.core.Method.GET

val showsRouting: RoutingHttpHandler = routes(

    "/" bind GET to HTMLMainPage(TopLevelPage.SHOWS, templateName = "shows/shows"),

    "/band/{uuid:[a-z0-9-]+$}" bind GET to HTMLSubPage(templateName = "/shows/band").from(bandInfo),

    "/manage" bind GET to HTMLSubPage().from(manage).withFilter(adminOnly),

)