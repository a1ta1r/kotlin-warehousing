package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.PlaceItemTable
import warehousing.tables.PlaceTable

class Place(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Place>(PlaceTable)

    var label by PlaceTable.label
    var description by PlaceTable.description

    val items by ItemInPlace referrersOn PlaceItemTable.place
}