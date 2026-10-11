package com.glitch.schema.model.shows

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BandResponse(
    val uuid: String,
    val name: String,
    @Json(name = "lastfm_url") val lastfmUrl: String?,
    val country: String? = null,
    val genre: String? = null,
    val headliner: Boolean? = null,
    @Json(name = "performance_date") val performanceDate: String? = null,
)
