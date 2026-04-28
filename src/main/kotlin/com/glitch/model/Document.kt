package com.glitch.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction


object DocumentTable : IntIdTable("document") {
    val filename = varchar("filename", 100)
    val path = varchar("path", 200)
}


/**
 * Whitelist mapping of a document name to its location on the file system.
 *
 * @property filename Simple filename of the document.
 * @property path Full file system path including the actual filename.
 */
class Document(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Document>(DocumentTable) {
        fun findByFilename(filename: String): Document? = transaction {
            find { DocumentTable.filename eq filename }.firstOrNull()
        }
    }

    var filename: String by DocumentTable.filename
    var path: String by DocumentTable.path
}
