package com.glitch.model.shows

import com.glitch.schema.model.shows.BandResponse
import com.glitch.schema.model.shows.EventResponse
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.dao.load
import org.jetbrains.exposed.v1.datetime.date
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID


enum class EventType {
    CONCERT,
    FESTIVAL,
}

object EventTable : IntIdTable("event") {
    val uuid = varchar("uuid", 45)

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
    companion object : IntEntityClass<Event>(EventTable) {
        override fun new(init: Event.() -> Unit): Event {
            return transaction {
                super.new {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }
        }

        override fun new(id: Int?, init: Event.() -> Unit): Event {
            return transaction {
                super.new(id) {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }
        }

        val getByUuid: (String) -> Event? = { uuid: String ->
            transaction {
                find { EventTable.uuid eq uuid }
                    .firstOrNull()
                    ?.load(Event::venue, Event::bandEvents, BandEvent::band)
            }
        }
    }

    var uuid: String by EventTable.uuid

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

    val bandEvents by BandEvent referrersOn BandEventTable.eventId

    val serialize: () -> EventResponse = {
        EventResponse(
            this.uuid,
            this.type.name,
            this.dateStart.toString(),
            this.dateEnd?.toString(),
            this.timeStart.toString(),
            this.timezone,
            this.venue.serialize(),
            this.eventName,
            this.comments,
            this.url,
            this.imageFilename,
            this.bandEvents.map {
                BandResponse(
                    it.band.uuid,
                    it.band.name,
                    it.band.lastfmUrl,
                    headliner = it.headliner,
                    performanceDate = it.performanceDate.toString(),
                )
            }.toList()
        )
    }
}