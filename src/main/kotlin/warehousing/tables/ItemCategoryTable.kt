package warehousing.tables

import org.jetbrains.exposed.dao.IntIdTable

object ItemCategoryTable : IntIdTable() {
    val item = reference("item_id", ItemTable).index()
    val category = reference("category_id", CategoryTable).index()

    init {
        index(true, item, category)
    }
}