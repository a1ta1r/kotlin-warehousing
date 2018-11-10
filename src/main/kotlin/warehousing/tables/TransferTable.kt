package warehousing.tables

object TransferTable : AbstractTable() {
    val transferPath = reference("transfer_path", TransferPathTable).index()
    val item = reference("item_id", ItemTable.id).index()
    val quantity = integer("quantity")
}