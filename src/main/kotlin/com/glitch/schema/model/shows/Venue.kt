package com.glitch.schema.model.shows

import com.fasterxml.jackson.annotation.JsonProperty


data class VenueResponse(
    val uuid: String,
    val name: String,

    val address: String,
    val city: String,
    val postcode: String,
    val state: String,
    val country: String,

    @get:JsonProperty("google_url") val googleUrl: String,
    @get:JsonProperty("venue_url") val venueUrl: String?,
)