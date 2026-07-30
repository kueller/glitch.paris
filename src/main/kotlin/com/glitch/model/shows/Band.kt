package com.glitch.model.shows

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass


object BandTable : IntIdTable("band") {
    val name = varchar("name", 100)
    val lastfmUrl = varchar("lastfm_url", 128).nullable()
}


class Band(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Band>(BandTable)

    var name: String by BandTable.name
    var lastfmUrl: String? by BandTable.lastfmUrl
}