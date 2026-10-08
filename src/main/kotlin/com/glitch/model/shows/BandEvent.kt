package com.glitch.model.shows

import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.date


object BandEventTable : IntIdTable("band_event") {
    val bandId = reference("band_id", BandTable)
    val eventId = reference("event_id", EventTable)

    val headliner = bool("headliner")
    val performanceDate = date("performance_date")
}


class BandEvent(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BandEvent>(BandEventTable)

    var band by Band referencedOn BandEventTable.bandId
    var event by Event referencedOn BandEventTable.eventId

    var headliner: Boolean by BandEventTable.headliner
    var performanceDate: LocalDate by BandEventTable.performanceDate
}