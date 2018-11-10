package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.ItemCategoryTable

class ItemCategories(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemCategories>(ItemCategoryTable)

    var item by Item referencedOn ItemCategoryTable.item
    var category by Category referencedOn ItemCategoryTable.category
}