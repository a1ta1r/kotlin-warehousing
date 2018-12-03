package warehousing.services

import warehousing.entities.PlainRecord

interface TransformerInterface {
    fun transformPlainRecordsToEntities(records: List<PlainRecord>): List<String>
    fun transformSinglePlainRecordToEntities(record: PlainRecord): String?
}