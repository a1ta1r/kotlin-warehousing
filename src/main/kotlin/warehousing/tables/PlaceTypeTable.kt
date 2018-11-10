package warehousing.tables

object PlaceTypeTable : AbstractTable() {
    val label = varchar("label", 64).index()
    val description = varchar("description", 255)
    val isSellingPoint = bool("is_selling_point").default(false).index()
}