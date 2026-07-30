package com.glitch.model.shows

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.datetime.date


object BandEventTable : Table("band_event") {
    val bandId = reference("band_id", BandTable)
    val eventId = reference("event_id", EventTable)

    val headliner = integer("headliner")
    val performanceDate = date("performance_date")
}