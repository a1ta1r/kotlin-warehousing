package warehousing.tables

import org.jetbrains.exposed.dao.IntIdTable
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

abstract class AbstractTable: IntIdTable() {
    var createdAt = datetime("created_at").default(DateTime.now(DateTimeZone.UTC))
}