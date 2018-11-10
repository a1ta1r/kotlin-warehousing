package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.CategoryTable

class Category(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Category>(CategoryTable)

    var label by CategoryTable.label
    var description by CategoryTable.description
}