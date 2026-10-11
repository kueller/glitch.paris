package com.glitch.schema.model.shows

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EventResponse(
    val uuid: String,
    val type: String,
    @Json(name = "start_date") val dateStart: String,
    @Json(name = "end_date") val dateEnd: String?,
    @Json(name = "start_time") val timeStart: String?,
    val timezone: String,
    val venue: VenueResponse,
    @Json(name = "event_name") val eventName: String?,
    val comments: String?,
    val url: String?,
    @Json(name = "image_filename") val imageFilename: String?,
    val bands: List<BandResponse>,
)
