package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.SaleTable

class Sale(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Sale>(SaleTable)

    val itemInPlace by ItemInPlace referencedOn SaleTable.placeItem
    var quantity by SaleTable.quantity
}