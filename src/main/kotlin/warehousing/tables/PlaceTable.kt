package warehousing.tables

import org.jetbrains.exposed.dao.IntIdTable

object PlaceTable : IntIdTable() {
    val label = varchar("label", 64).uniqueIndex()
    val description = varchar("description", 255)
}