package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.TransferTable

class Transfer(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Transfer>(TransferTable)

    var quantity by TransferTable.quantity

    val item by Item referencedOn TransferTable.item
    val transferPath by TransferPath referrersOn TransferTable.transferPath
}