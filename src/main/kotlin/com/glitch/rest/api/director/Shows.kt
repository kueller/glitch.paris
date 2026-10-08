package com.glitch.rest.api.director

import com.glitch.model.shows.Band
import com.glitch.model.shows.Event
import com.glitch.model.shows.Venue
import com.glitch.schema.api.director.BandsSearchResponse
import com.glitch.schema.api.director.NewBandRequest
import com.glitch.schema.api.director.NewEventRequest
import com.glitch.schema.api.director.NewVenueRequest
import com.glitch.schema.api.director.VenueSearchResponse
import com.glitch.schema.model.shows.BandResponse
import com.glitch.schema.model.shows.VenueResponse
import com.glitch.util.UNICODE_REGEX
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.path


val searchBandByName: HttpHandler = { request: Request ->
    val name: String = request.path("query") ?: "xxxxxxxxx"
    if (!name.matches(UNICODE_REGEX)) {
        Response(Status.BAD_REQUEST)
    } else {
        val responseLens = Body.auto<BandsSearchResponse>().toLens()
        Response(Status.OK).with(
            responseLens of BandsSearchResponse(
                Band.findByName(name).map { it.serialize() }.toList()
            )
        )
    }
}


val addNewBand: HttpHandler = { request: Request ->
    val requestLens = Body.auto<NewBandRequest>().toLens()
    val responseLens = Body.auto<BandResponse>().toLens()

    try {
        val bandData = requestLens.extract(request)
        try {
            Response(Status.OK).with(responseLens of Band.new {
                name = bandData.name
                lastfmUrl = bandData.lastfmUrl
            }.serialize())
        } catch (e: Exception) {
            Response(Status.INTERNAL_SERVER_ERROR)
        }
    } catch (e: Exception) {
        Response(Status.BAD_REQUEST).body(e.message ?: e.toString())
    }
}


val searchVenueByName: HttpHandler = { request: Request ->
    val name: String = request.path("query") ?: "xxxxxxxxx"
    if (!name.matches(UNICODE_REGEX)) {
        Response(Status.BAD_REQUEST)
    } else {
        val responseLens = Body.auto<VenueSearchResponse>().toLens()
        Response(Status.OK).with(
            responseLens of VenueSearchResponse(
                Venue.findByName(name).map { it.serialize() }.toList()
            )
        )
    }
}


val addNewVenue: HttpHandler = { request: Request ->
    val requestLens = Body.auto<NewVenueRequest>().toLens()
    val responseLens = Body.auto<VenueResponse>().toLens()

    try {
        val venueData = requestLens.extract(request)
        try {
            Response(Status.OK).with(responseLens of Venue.new {
                name = venueData.name
                address = venueData.address
                city = venueData.city
                postcode = venueData.postcode
                state = venueData.state
                country = venueData.country
                googleUrl = venueData.googleUrl
                venueUrl = venueData.venueUrl
            }.serialize())
        } catch (e: Exception) {
            println(e.message.toString())
            Response(Status.INTERNAL_SERVER_ERROR)
        }
    } catch (e: Exception) {
        Response(Status.BAD_REQUEST).body(e.message ?: e.toString())
    }
}


val addNewEvent: HttpHandler = { request: Request ->
    val requestLens = Body.auto<NewEventRequest>().toLens()

    try {
        val eventData = requestLens.extract(request)
    } catch (e: Exception) {
        Response(Status.BAD_REQUEST).body(e.message ?: e.toString())
    }
    Response(Status.OK)
}