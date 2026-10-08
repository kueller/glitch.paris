package com.glitch.schema.model.shows

import com.fasterxml.jackson.annotation.JsonProperty


data class EventResponse(
    val uuid: String,
    val type: String,

    @get:JsonProperty("date_start") val dateStart: String,
    @get:JsonProperty("date_end") val dateEnd: String?,
    @get:JsonProperty("time_start") val timeStart: String,
    val timezone: String,

    val venue: VenueResponse,

    @get:JsonProperty("event_name") val eventName: String?,
    val comments: String?,

    val url: String?,
    @get:JsonProperty("image_filename") val imageFilename: String?,

    val bands: List<BandResponse>,
)