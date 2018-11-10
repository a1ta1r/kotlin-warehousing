package warehousing.tables

object SaleTable: AbstractTable() {
    val placeItem = reference("place_item", PlaceItemTable).index()
    val quantity = integer("quantity")
}