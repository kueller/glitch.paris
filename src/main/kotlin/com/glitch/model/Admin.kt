package com.glitch.model

import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object AdminTable : IntIdTable("admin") {
    val password = varchar("password", 256)
    val salt = varchar("salt", 256)
}

class Admin(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Admin>(AdminTable) {
        fun getAdminCredentials(): Admin? = transaction {
            Admin.findById(1)
        }
    }

    var password: String by AdminTable.password
    var salt: String by AdminTable.salt
}