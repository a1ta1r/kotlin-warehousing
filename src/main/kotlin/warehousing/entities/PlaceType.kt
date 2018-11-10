package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.PlaceTypeTable

class PlaceType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PlaceType>(PlaceTypeTable)

    var label by PlaceTypeTable.label
    var description by PlaceTypeTable.description
    var isSellingPoint by PlaceTypeTable.isSellingPoint
}