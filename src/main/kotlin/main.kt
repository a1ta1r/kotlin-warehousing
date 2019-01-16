import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import warehousing.entities.PlainRecord
import warehousing.repositories.PlainRecordRepository
import warehousing.services.SqliteTransformer
import warehousing.services.XLSXWriter
import warehousing.tables.*

fun main(args: Array<String>) {

    transaction(DbConnection.sqlite) {
        SchemaUtils.create(
                WarehousingTable
        )
    }
    val sqliteRepo = PlainRecordRepository(DbConnection.sqlite)
    val records = sqliteRepo.getPlainRecords()
    transaction(DbConnection.db) {
        SchemaUtils.create(
                ItemTable,
                CategoryTable,
                ItemCategoryTable,
                PlaceTable,
                PlaceItemTable,
                TransferPathTable,
                ItemTransferTable
        )

    }
    transaction(DbConnection.db) {
        addLogger(StdOutSqlLogger)
        addLogger(Slf4jSqlDebugLogger)
        ItemTransferTable.deleteAll()
        TransferPathTable.deleteAll()
        PlaceItemTable.deleteAll()
        PlaceTable.deleteAll()
        ItemCategoryTable.deleteAll()
        CategoryTable.deleteAll()
        ItemTable.deleteAll()
        SqliteTransformer().transformPlainRecordsToEntities(records).forEach {
            println("TRANSFER ERROR: $it")
        }
    }

    XLSXWriter(DbConnection.db).fillXLSX()


}