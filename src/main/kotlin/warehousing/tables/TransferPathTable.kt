package warehousing.tables

import org.jetbrains.exposed.dao.IntIdTable

object TransferPathTable : IntIdTable() {
    val placeFrom = reference("place_from_id", PlaceTable)
    val placeTo = reference("place_in_id", PlaceTable)

    init {
        index(true, placeFrom, placeTo)
    }
}