package warehousing.tables

object PlaceItemTable: AbstractTable() {
    val place = reference("place", PlaceTable).index()
    val item = reference("item", ItemTable).index()
    val quantity = integer("quantity")
    val pricePerUnit = integer("price_per_unit")
}