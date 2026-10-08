package com.glitch.rest

import com.glitch.config.TopLevelPage
import com.glitch.rest.api.apiRouting
import com.glitch.rest.filter.verifyAdmin
import com.glitch.rest.web.HTMLMainPage
import com.glitch.rest.web.HTMLSubPage
import com.glitch.rest.web.documentHandler
import com.glitch.rest.web.errorPageFilter
import com.glitch.rest.web.favicon
import com.glitch.rest.web.hello
import com.glitch.rest.web.login
import com.glitch.rest.web.night
import com.glitch.rest.web.staticHandler
import com.glitch.rest.web.webRouting
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes


val restRouting: RoutingHttpHandler = routes(

    webRouting,

    "/api" bind apiRouting
)
