package com.glitch.schema.api.director

import com.glitch.model.shows.EventType
import com.glitch.model.shows.Genre
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

@JsonClass(generateAdapter = true)
data class NewBandRequest(
    val name: String,
    val origin: String? = null,
    val genre: Genre? = null,
    @Json(name = "lastfm_url") val lastfmUrl: String? = null,
)

@JsonClass(generateAdapter = true)
data class NewVenueRequest(
    val name: String,
    val address: String,
    val city: String,
    val postcode: String,
    val state: String,
    val country: String,
    @Json(name = "google_url") val googleUrl: String,
    @Json(name = "venue_url") val venueUrl: String? = null,
)

@JsonClass(generateAdapter = true)
data class BandTimeSlotRequest(
    val headliner: Boolean,
    @Json(name = "band") val bandId: String,
)

@JsonClass(generateAdapter = true)
data class ScheduleRequest(
    val date: LocalDate,
    val bands: List<BandTimeSlotRequest>,
)

@JsonClass(generateAdapter = true)
data class NewEventRequest(
    val type: EventType,
    val timezone: String,
    val schedule: List<ScheduleRequest>,
    @Json(name = "venue") val venueId: String,
    @Json(name = "start_date") val startDate: LocalDate,
    val url: String? = null,
    val image: String? = null,
    val comments: String? = null,
    @Json(name = "start_time") val startTime: LocalTime? = null,
    @Json(name = "end_date") val endDate: LocalDate? = null,
    @Json(name = "event_name") val eventName: String? = null,
)