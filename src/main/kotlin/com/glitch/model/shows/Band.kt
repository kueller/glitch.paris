package com.glitch.model.shows

import com.glitch.schema.model.shows.BandResponse
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.*

enum class Genre(val displayName: String) {
    ROCK("Rock"),
    POP("Pop"),
    ELECTRONIC("Electronic"),
    METAL("Metal"),
    ALT("Alternative"),
    POST_ROCK("Post-Rock"),
    MAHOGANY("Heavy Mahogany"),
    FOLK("Folk"),
    INDIE("Indie"),
    SHOEGAZE("Shoegaze"),
    HIP_HOP("Hip Hop"),
    PUNK("Punk"),
    ORCHESTRAL("Orchestral"),
    LATIN("Latin"),
    MUSICAL("Musical"),
    PROG("Progressive Rock"),
    EXPERIMENTAL("Experimental"),
    DREAM_POP("Dream Pop"),
    HYPERPOP("Hyperpop"),
    GABBER("Gabber"),
    TECHNO("Techno"),
    HOUSE("House"),
    JAZZ("Jazz"),
    NOISE("Noise"),
    AMBIENT("Ambient"),
    TRIP_HOP("Trip-Hop"),
    RNB("RnB"),
}

object BandTable : IntIdTable("band") {
    val uuid = varchar("uuid", 45)
    val name = varchar("name", 100)
    val lastfmUrl = varchar("lastfm_url", 128).nullable()
    val country = varchar("country", 5).nullable()
    val genre = varchar("genre", 100).nullable()
}

class Band(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Band>(BandTable) {
        override fun new(init: Band.() -> Unit): Band =
            transaction {
                super.new {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }

        override fun new(id: Int?, init: Band.() -> Unit): Band =
            transaction {
                super.new(id) {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }

        val getByUuid: (String) -> Band? = { uuid: String ->
            transaction {
                find { BandTable.uuid eq uuid }.firstOrNull()
            }
        }

        val findByName: (String) -> List<Band> = { name: String ->
            transaction {
                find { BandTable.name like "%$name%" }.toList()
            }
        }
    }

    var uuid: String by BandTable.uuid
    var name: String by BandTable.name
    var lastfmUrl: String? by BandTable.lastfmUrl
    var country: String? by BandTable.country
    var genre: String? by BandTable.genre

    val serialize: () -> BandResponse = {
        BandResponse(
            this.uuid,
            this.name,
            this.lastfmUrl,
            this.country,
            Genre.entries.find { it.name == this.genre }?.displayName ?: this.genre
        )
    }
}
