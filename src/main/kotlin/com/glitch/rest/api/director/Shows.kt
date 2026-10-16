package com.glitch.rest.api.director

import com.glitch.exception.*
import com.glitch.model.shows.Band
import com.glitch.model.shows.BandEvent
import com.glitch.model.shows.Event
import com.glitch.model.shows.Venue
import com.glitch.rest.incomingJson
import com.glitch.rest.outgoingJson
import com.glitch.schema.api.director.BandsSearchResponse
import com.glitch.schema.api.director.NewBandRequest
import com.glitch.schema.api.director.NewEventRequest
import com.glitch.schema.api.director.NewVenueRequest
import com.glitch.schema.api.director.ScheduleRequest
import com.glitch.schema.api.director.VenueSearchResponse
import com.glitch.util.UNICODE_REGEX
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.http4k.core.*
import org.http4k.routing.path
import org.jetbrains.exposed.v1.dao.flushCache
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

val searchBandByName: HttpHandler = { request: Request ->
    val name: String = request.path("query") ?: "xxxxxxxxx"
    if (!name.matches(UNICODE_REGEX)) {
        throw BadFormatting(name)
    } else {
        Response(Status.OK).with(
            outgoingJson(
                BandsSearchResponse(
                    Band.findByName(name).map { it.serialize() }.toList(),
                ),
            ),
        )
    }
}

val addNewBand: HttpHandler = { request: Request ->
    val bandData = incomingJson<NewBandRequest>(request)

    Response(Status.OK).with(
        outgoingJson(
            Band
                .new {
                    name = bandData.name
                    lastfmUrl = bandData.lastfmUrl
                    country = bandData.origin
                    genre = bandData.genre?.name
                }.serialize(),
        ),
    )
}

val searchVenueByName: HttpHandler = { request: Request ->
    val name: String = request.path("query") ?: "xxxxxxxxx"
    if (!name.matches(UNICODE_REGEX)) {
        throw BadFormatting(name)
    } else {
        Response(Status.OK).with(
            outgoingJson(
                VenueSearchResponse(
                    Venue.findByName(name).map { it.serialize() }.toList(),
                ),
            ),
        )
    }
}

val addNewVenue: HttpHandler = { request: Request ->
    val venueData = incomingJson<NewVenueRequest>(request)

    Response(Status.OK).with(
        outgoingJson(
            Venue
                .new {
                    name = venueData.name
                    address = venueData.address
                    city = venueData.city
                    postcode = venueData.postcode
                    state = venueData.state
                    country = venueData.country
                    googleUrl = venueData.googleUrl
                    venueUrl = venueData.venueUrl
                }.serialize(),
        ),
    )
}

val addNewEvent: HttpHandler = { request: Request ->
    val eventData = incomingJson<NewEventRequest>(request)

    val venue = Venue.getByUuid(eventData.venueId) ?: throw VenueNotFound(eventData.venueId)

    if (eventData.endDate != null && eventData.endDate <= eventData.startDate) {
        throw InvalidDateRange(eventData.endDate)
    }

    val bandData = mutableMapOf<Band, Pair<LocalDate, Boolean>>()

    for (eventSlotDate: ScheduleRequest in eventData.schedule) {
        if (eventSlotDate.date < eventData.startDate) {
            throw OutOfRange(eventSlotDate.date)
        }

        if (eventData.endDate != null && eventSlotDate.date > eventData.endDate) {
            throw OutOfRange(eventSlotDate.date)
        }

        var headliners = 0
        for (bandSlot in eventSlotDate.bands) {
            val band = Band.getByUuid(bandSlot.bandId) ?: throw BandNotFound(bandSlot.bandId)

            if (bandSlot.headliner) headliners++

            bandData[band] = Pair(eventSlotDate.date, bandSlot.headliner)
        }

        if (headliners == 0) {
            throw NoHeadliner(eventSlotDate.date)
        } else if (headliners > 1) {
            throw MultipleHeadliners(eventSlotDate.date)
        }
    }

    val event =
        Event.new {
            this.type = eventData.type
            this.eventName = eventData.eventName
            this.dateStart = eventData.startDate
            this.dateEnd = eventData.endDate
            this.timeStart = eventData.startTime?.let {
                LocalDateTime(eventData.startDate, eventData.startTime)
            }
            this.timezone = eventData.timezone
            this.venue = venue
            this.url = eventData.url
            this.imageFilename = eventData.image
            this.comments = eventData.comments
        }

    event.flush()

    bandData.map {
        BandEvent.new {
            this.event = event
            this.band = it.key
            this.performanceDate = it.value.first
            this.headliner = it.value.second
        }
    }

    transaction { flushCache() }
    Response(Status.OK).with(outgoingJson(event.serialize()))
}
