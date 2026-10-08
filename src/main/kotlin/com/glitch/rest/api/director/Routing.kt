package com.glitch.rest.api.director

import com.glitch.rest.filter.adminOnlyApi
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.routes
import org.http4k.routing.bind
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST

val directorApiRouting: RoutingHttpHandler = routes(

    "/shows/bands/{query}/search" bind GET to searchBandByName,

    "/shows/venues/{query}/search" bind GET to searchVenueByName,

    "/shows/band" bind POST to addNewBand,

    "/shows/venue" bind POST to addNewVenue,

    "/shows/event" bind POST to addNewEvent,

).withFilter(adminOnlyApi)