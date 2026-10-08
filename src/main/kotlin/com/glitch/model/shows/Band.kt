package com.glitch.model.shows

import com.glitch.schema.model.shows.BandResponse
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.like
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID


object BandTable : IntIdTable("band") {
    val uuid = varchar("uuid", 45)
    val name = varchar("name", 100)
    val lastfmUrl = varchar("lastfm_url", 128).nullable()
}


class Band(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Band>(BandTable) {
        override fun new(init: Band.() -> Unit): Band {
            return transaction {
                super.new {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
            }
        }

        override fun new(id: Int?, init: Band.() -> Unit): Band {
            return transaction {
                super.new(id) {
                    init()
                    uuid = UUID.randomUUID().toString()
                }
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

    val serialize: () -> BandResponse = {
        BandResponse(
            this.uuid,
            this.name,
            this.lastfmUrl,
        )
    }
}