package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.ItemCategoryTable
import warehousing.tables.ItemTable

class Item(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Item>(ItemTable)

    var label by ItemTable.label
    var description by ItemTable.description

    val categories by ItemCategories referrersOn ItemCategoryTable.item
}