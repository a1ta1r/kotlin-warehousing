package warehousing.tables

import org.jetbrains.exposed.dao.IntIdTable

object ItemTransferTable : IntIdTable() {
    val transferPath = reference("transfer_path_id", TransferPathTable).index()
    val item = reference("item_id", ItemTable).index()
    val quantity = integer("quantity")
    val createdAt = datetime("created_at")
}