package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.ItemTransferTable

class Transfer(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Transfer>(ItemTransferTable)

    var quantity by ItemTransferTable.quantity
    var datetime by ItemTransferTable.createdAt

    val item by Item referencedOn ItemTransferTable.item
    val transferPath by TransferPath referencedOn ItemTransferTable.transferPath
}