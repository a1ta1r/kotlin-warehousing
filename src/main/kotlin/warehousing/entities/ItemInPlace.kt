package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.PlaceItemTable

class ItemInPlace(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ItemInPlace>(PlaceItemTable)

    var item by Item referencedOn PlaceItemTable.item
    var place by Place referencedOn PlaceItemTable.place
    var quantity by PlaceItemTable.quantity
    var pricePerUnit by PlaceItemTable.pricePerUnit
}