package com.glitch.rest.web

import com.glitch.config.TopLevelPage
import com.glitch.rest.filter.verifyAdmin
import com.glitch.rest.web.shows.showsRouting
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

val webRouting: RoutingHttpHandler = routes(

    "/" bind GET to HTMLMainPage(TopLevelPage.HOME).from(hello),

    "/night" bind GET to HTMLMainPage(TopLevelPage.NIGHT).from(night),

    "/etc" bind GET to HTMLMainPage(TopLevelPage.ETC),

    "/shows" bind showsRouting,

    "/login-admin" bind GET to HTMLSubPage().from(login),
    "/login-admin" bind POST to HTMLSubPage().from(login).withFilter(verifyAdmin),

    "/favicon.ico" bind GET to favicon,

    "/documents/{filename:[^\\/:*?\"<>|]+$}" bind GET to documentHandler,

    "/static/{path:.*}" bind GET to staticHandler

)