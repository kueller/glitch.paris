package com.glitch.schema.model.shows

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

data class BandResponse(
    val uuid: String,
    val name: String,
    @get:JsonProperty("lastfm_url") val lastfmUrl: String?,

    @get:JsonInclude(JsonInclude.Include.NON_NULL) val headliner: Boolean? = null,

    @get:JsonProperty("performance_date")
    @get:JsonInclude(JsonInclude.Include.NON_NULL)
    val performanceDate: String? = null,
)
