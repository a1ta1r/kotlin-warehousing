package warehousing.tables

object PlaceTable : AbstractTable() {
    val label = varchar("label", 64).index()
    val description = varchar("description", 255)
    val placeType = reference("place_type_id", PlaceTypeTable).index()
}