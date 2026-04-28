package com.glitch.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


object NightImageTable : IntIdTable("night_images") {
    val title = varchar("title", 100)
    val filename = varchar("filename", 100)
    val altText = varchar("alt_text", 500)
}


/**
 * Directory of Paris by night photos.
 *
 * @property title Title of the image.
 * @property filename Filename. prev_{filename} will be the thumbnail.
 * @property altText Alt text for accessibility.
 */
class NightImage(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<NightImage>(NightImageTable) {
        fun getAll(): List<NightImage> {
            return transaction { NightImage.all().toList() }
        }
    }

    var title: String by NightImageTable.title
    var filename: String by NightImageTable.filename
    var altText: String by NightImageTable.altText
}