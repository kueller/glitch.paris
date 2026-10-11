package com.glitch.schema.model.shows

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VenueResponse(
    val uuid: String,
    val name: String,
    val address: String,
    val city: String,
    val postcode: String,
    val state: String,
    val country: String,
    @Json(name = "google_url") val googleUrl: String,
    @Json(name = "venue_url") val venueUrl: String?,
)
