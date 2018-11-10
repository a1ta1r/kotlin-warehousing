package warehousing.tables

object ItemCategoryTable : AbstractTable() {
    val item = reference("item_id", ItemTable).index()
    val category = reference("category_id", CategoryTable).index()
}