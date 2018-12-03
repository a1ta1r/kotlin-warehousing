package warehousing.tables

import org.jetbrains.exposed.sql.Table


object WarehousingTable : Table() {
    val item = varchar("item", 255).nullable()
    val placeFrom = varchar("placeFrom", 255).nullable()
    val placeTo = varchar("placeTo", 255).nullable()
    val quantity = integer("quantity").nullable()
    val itemCategories = varchar("itemCategories", 255).nullable()
    val time = datetime("time").nullable()
}