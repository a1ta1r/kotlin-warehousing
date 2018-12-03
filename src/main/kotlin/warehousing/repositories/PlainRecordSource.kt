package warehousing.repositories

import warehousing.entities.PlainRecord

interface PlainRecordSource {
    fun getPlainRecords(): List<PlainRecord>
}