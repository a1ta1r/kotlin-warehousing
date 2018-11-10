package warehousing.tables

object TransferPathTable : AbstractTable() {
    val placeFrom = reference("place_from_id", PlaceTable).index()
    val placeTo = reference("place_in_id", PlaceTable).index()
}