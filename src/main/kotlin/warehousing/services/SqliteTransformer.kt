package warehousing.services

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime
import warehousing.entities.PlainRecord
import warehousing.tables.*

class SqliteTransformer : TransformerInterface {
    private fun notEnoughData(record: PlainRecord) = "Can not transfer row $record."
    override fun transformSinglePlainRecordToEntities(record: PlainRecord): String? {
        if (record.placeTo == "" || record.placeTo == "-" || record.quantity == 0 || record.item == "" || record.itemCategories.count() == 0) {
            return notEnoughData(record)
        }

        val itemId = insertItemIfNotExists(record.item)

        val categoryIds = record.itemCategories.map { insertCategoryIfNotExists(it) }

        categoryIds.map { insertItemCategoryIfNotExists(itemId, it) }

        if (record.placeFrom == "" || record.placeTo == "-") {
            val placeToId = insertPlaceIfNotExists(record.placeTo)
            insertOrUpdateItemPlaceTo(itemId, placeToId, record.quantity)
            return null
        }

        val placeFromId = insertPlaceIfNotExists(record.placeFrom)
        val placeToId = insertPlaceIfNotExists(record.placeTo)

        insertOrUpdateItemPlaceFrom(itemId, placeFromId, record.quantity)
        insertOrUpdateItemPlaceTo(itemId, placeToId, record.quantity)

        val transferPathId = insertTransferPathIfNotExists(placeFromId, placeToId)
        insertTransfer(transferPathId, itemId, record.quantity, record.time)
        return null
    }

    override fun transformPlainRecordsToEntities(records: List<PlainRecord>): List<String> {
        return records.mapNotNull { transformSinglePlainRecordToEntities(it) }
    }

    private fun insertItemIfNotExists(item: String): EntityID<Int> {
        val existingItem = ItemTable.select { ItemTable.label eq item }.firstOrNull()
                ?: return ItemTable.insertAndGetId {
                    it[label] = item
                    it[description] = ""
                }
        return existingItem[ItemTable.id]
    }

    private fun insertCategoryIfNotExists(category: String): EntityID<Int> {
        val existingCategory = CategoryTable.select { CategoryTable.label eq category }.firstOrNull()
                ?: return CategoryTable.insertAndGetId {
                    it[label] = category
                    it[description] = ""
                }
        return existingCategory[CategoryTable.id]
    }

    private fun insertItemCategoryIfNotExists(itemID: EntityID<Int>, categoryID: EntityID<Int>): EntityID<Int> {
        val existingItemCategory = ItemCategoryTable.select {
            ItemCategoryTable.item eq itemID and (ItemCategoryTable.category eq categoryID)
        }.firstOrNull()
                ?: return ItemCategoryTable.insertAndGetId {
                    it[item] = itemID
                    it[category] = categoryID
                }
        return existingItemCategory[ItemCategoryTable.id]
    }

    private fun insertPlaceIfNotExists(place: String): EntityID<Int> {
        val existingPlace = PlaceTable.select { PlaceTable.label eq place }.firstOrNull()
                ?: return PlaceTable.insertAndGetId {
                    it[label] = place
                    it[description] = ""
                }
        return existingPlace[PlaceTable.id]
    }

    private fun insertOrUpdateItemPlaceFrom(itemID: EntityID<Int>, placeID: EntityID<Int>, itemQuantity: Int): EntityID<Int> {
        val existingItemPlace = PlaceItemTable.select {
            PlaceItemTable.item eq itemID and (PlaceItemTable.place eq placeID)
        }.firstOrNull()
        return if (existingItemPlace == null) {
            PlaceItemTable.insertAndGetId {
                it[item] = itemID
                it[place] = placeID
                it[quantity] = -itemQuantity
            }
        } else {
            EntityID(PlaceItemTable.update({ PlaceItemTable.item eq itemID and (PlaceItemTable.place eq placeID) }) {
                with(SqlExpressionBuilder) {
                    it.update(PlaceItemTable.quantity, PlaceItemTable.quantity - itemQuantity)
                }
            }, PlaceItemTable)
        }
    }

    private fun insertOrUpdateItemPlaceTo(itemID: EntityID<Int>, placeID: EntityID<Int>, itemQuantity: Int): EntityID<Int> {
        val existingItemPlace = PlaceItemTable.select {
            PlaceItemTable.item eq itemID and (PlaceItemTable.place eq placeID)
        }.firstOrNull()
        return if (existingItemPlace == null) {
            PlaceItemTable.insertAndGetId {
                it[item] = itemID
                it[place] = placeID
                it[quantity] = itemQuantity
            }
        } else {
            EntityID(PlaceItemTable.update({ PlaceItemTable.item eq itemID and (PlaceItemTable.place eq placeID) }) {
                with(SqlExpressionBuilder) {
                    it.update(PlaceItemTable.quantity, PlaceItemTable.quantity + itemQuantity)
                }
            }, PlaceItemTable)
        }
    }

    private fun insertTransferPathIfNotExists(placeFromID: EntityID<Int>, placeToID: EntityID<Int>): EntityID<Int> {
        val existingTransferPath = TransferPathTable.select {
            TransferPathTable.placeFrom eq placeFromID and (TransferPathTable.placeTo eq placeToID)
        }.firstOrNull()
                ?: return TransferPathTable.insertAndGetId {
                    it[placeFrom] = placeFromID
                    it[placeTo] = placeToID
                }
        return existingTransferPath[TransferPathTable.id]
    }

    private fun insertTransfer(transferPathID: EntityID<Int>, itemID: EntityID<Int>, itemQuantity: Int, dt: DateTime) {
        ItemTransferTable.insert {
            it[transferPath] = transferPathID
            it[item] = itemID
            it[quantity] = itemQuantity
            it[createdAt] = dt
        }
    }
}