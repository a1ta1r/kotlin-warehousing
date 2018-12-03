package warehousing.repositories

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import warehousing.entities.PlainRecord
import warehousing.tables.WarehousingTable
import warehousing.tables.WarehousingTable.item
import warehousing.tables.WarehousingTable.itemCategories
import warehousing.tables.WarehousingTable.placeFrom
import warehousing.tables.WarehousingTable.placeTo
import warehousing.tables.WarehousingTable.quantity
import warehousing.tables.WarehousingTable.time

class PlainRecordRepository(private val db: Database) : PlainRecordSource {
    override fun getPlainRecords(): List<PlainRecord> {
        return transaction(db) {
            WarehousingTable.selectAll().map {
                recordToEntityMapper(it)
            }
        }
    }

    private fun recordToEntityMapper(record: ResultRow): PlainRecord {
        return PlainRecord(
                item = record[item] ?: "",
                placeFrom = record[placeFrom] ?: "",
                placeTo = record[placeTo] ?: "",
                quantity = record[quantity] ?: 0,
                itemCategories = (record[itemCategories] ?: "").split("; ").toTypedArray(),
                time = record[time] ?: DateTime()
        )
    }
}