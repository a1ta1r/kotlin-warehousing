package warehousing.entities

import org.joda.time.DateTime

data class PlainRecord(
        val item: String = "",
        val placeFrom: String = "",
        val placeTo: String = "",
        val quantity: Int = 0,
        val itemCategories: Array<String> = arrayOf(),
        val time: DateTime = DateTime()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlainRecord

        if (!itemCategories.contentEquals(other.itemCategories)) return false

        return true
    }

    override fun hashCode(): Int {
        return itemCategories.contentHashCode()
    }
}