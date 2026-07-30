package com.glitch.model.shows

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


object VenueTable : IntIdTable("venuue") {
    val name = varchar("name", 256)
    val address = varchar("address", 256)
    val city = varchar("city", 100)
    val postcode = varchar("postcode", 16)

    val googleUrl = varchar("google_url", 128)
    val venueUrl = varchar("venue_url", 128).nullable()
}


class Venue(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Venue>(VenueTable)

    var name: String by VenueTable.name
    var address: String by VenueTable.address
    var city: String by VenueTable.city
    var postcode: String by VenueTable.postcode

    var googleUrl: String by VenueTable.googleUrl
    var venueUrl: String? by VenueTable.venueUrl
}