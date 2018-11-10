package warehousing.tables

object ItemTable : AbstractTable() {
    val label = varchar("label", 64).index()
    val description = varchar("description", 255)
}