package com.glitch.model.shows

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.datetime.datetime


enum class EventType {
    CONCERT,
    FESTIVAL,
}

object EventTable : IntIdTable() {
    val type = enumerationByName("type", 8, EventType::class)

    val dateStart = date("date_start")
    val dateEnd = date("date_end").nullable()
    val timeStart = datetime("time_start")
    val timezone = varchar("timezone", 100)

    val venueId = reference("venue_id", VenueTable)

    val eventName = varchar("event_name", 256).nullable()
    val comments = text("comments").nullable()

    val url = varchar("url", 128).nullable()
    val imageFilename = varchar("image_filename", 128).nullable()
}


class Event(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Event>(EventTable)

    var type: EventType by EventTable.type

    var dateStart: LocalDate by EventTable.dateStart
    var dateEnd: LocalDate? by EventTable.dateEnd
    var timeStart: LocalDateTime by EventTable.timeStart
    var timezone: String by EventTable.timezone

    var venue by Venue referencedOn EventTable.venueId

    var eventName: String? by EventTable.eventName
    var comments: String? by EventTable.comments

    var url: String? by EventTable.url
    var imageFilename: String? by EventTable.imageFilename

    var bands by Band via BandEventTable
}