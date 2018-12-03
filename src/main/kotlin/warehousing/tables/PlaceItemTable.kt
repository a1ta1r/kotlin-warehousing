package warehousing.tables

import org.jetbrains.exposed.dao.IntIdTable

object PlaceItemTable : IntIdTable() {
    val place = reference("place", PlaceTable).index()
    val item = reference("item", ItemTable).index()
    val quantity = integer("quantity")
}