package com.glitch.model.shows

import com.glitch.schema.model.shows.VenueResponse
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID


object VenueTable : IntIdTable("venue") {
    val uuid = varchar("uuid", 45)
    val name = varchar("name", 256)
    val address = varchar("address", 256)
    val city = varchar("city", 100)
    val postcode = varchar("postcode", 16)
    val state = varchar("state", 128)
    val country = varchar("country", 5)

    val googleUrl = varchar("google_url", 128)
    val venueUrl = varchar("venue_url", 128).nullable()
}


class Venue(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Venue>(VenueTable) {
        override fun new(init: Venue.() -> Unit): Venue{
            return transaction {
                super.new {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }
        }

        override fun new(id: Int?, init: Venue.() -> Unit): Venue {
            return transaction {
                super.new(id) {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }
        }

        val findByName: (String) -> List<Venue> = { name: String ->
            transaction {
                find { VenueTable.name like "%$name%" }.toList()
            }
        }
    }

    var uuid: String by VenueTable.uuid

    var name: String by VenueTable.name
    var address: String by VenueTable.address
    var city: String by VenueTable.city
    var postcode: String by VenueTable.postcode
    var state: String by VenueTable.state
    var country: String by VenueTable.country

    var googleUrl: String by VenueTable.googleUrl
    var venueUrl: String? by VenueTable.venueUrl

    val serialize: () -> VenueResponse = {
        VenueResponse(
            this.uuid,
            this.name,
            this.address,
            this.city,
            this.postcode,
            this.state,
            this.country,
            this.googleUrl,
            this.venueUrl
        )
    }
}