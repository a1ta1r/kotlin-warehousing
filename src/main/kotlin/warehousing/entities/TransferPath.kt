package warehousing.entities

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import warehousing.tables.TransferPathTable

class TransferPath(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TransferPath>(TransferPathTable)

    val placeFrom by Place referencedOn TransferPathTable.placeFrom
    val placeTo by Place referencedOn TransferPathTable.placeTo
}