package com.glitch.schema.api.director

import com.glitch.schema.model.shows.BandResponse
import com.glitch.schema.model.shows.VenueResponse

data class BandsSearchResponse(val bands: List<BandResponse>)

data class VenueSearchResponse(val venues: List<VenueResponse>)