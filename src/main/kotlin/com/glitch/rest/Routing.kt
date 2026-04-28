package com.glitch.rest

import com.glitch.config.TopLevelPage
import org.http4k.core.Method.GET
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes


val restRouting: RoutingHttpHandler = routes(

    "/" bind GET to HTMLMainPage(TopLevelPage.HOME).from(hello),

    "/night" bind GET to HTMLMainPage(TopLevelPage.NIGHT).from(night),

    "/etc" bind GET to HTMLMainPage(TopLevelPage.ETC),

    "/favicon.ico" bind GET to favicon,

    "/documents/{filename:[^\\/:*?\"<>|]+$}" bind GET to documentHandler,

    "/static/{path:.*}" bind GET to staticHandler

).withFilter(errorPageFilter)