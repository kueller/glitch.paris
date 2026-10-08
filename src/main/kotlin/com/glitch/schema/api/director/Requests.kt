package com.glitch.schema.api.director

import com.fasterxml.jackson.annotation.JsonProperty


data class NewBandRequest(
    val name: String,
    @get:JsonProperty("lastfm_url") val lastfmUrl: String? = null
)


data class NewVenueRequest(
    val name: String,
    val address: String,
    val city: String,
    val postcode: String,
    val state: String,
    val country: String,
    @get:JsonProperty("google_url") val googleUrl: String,
    @get:JsonProperty("venue_url") val venueUrl: String? = null,
)


data class BandTimeSlotRequest(
    val selected: Boolean,
    @get:JsonProperty("band") val bandId: String,
)


data class ScheduleRequest(
    val date: String,
    val bands: List<BandTimeSlotRequest>
)


data class NewEventRequest(
    val type: String,
    val timezone: String,
    val schedule: List<ScheduleRequest>,
    @get:JsonProperty("venue") val venueId: String,
    @get:JsonProperty("start_date") val startDate: String,
    @get:JsonProperty("start_time") val startTime: String,

    val url: String? = null,
    val image: String? = null,
    val comments: String? = null,
    @get:JsonProperty("end_date") val endDate: String? = null,
    @get:JsonProperty("event_name") val eventName: String? = null,
)
