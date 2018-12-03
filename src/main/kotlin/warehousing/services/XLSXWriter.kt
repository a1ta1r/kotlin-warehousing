package warehousing.services

import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import warehousing.entities.*
import java.io.FileOutputStream

class XLSXWriter(private val db: Database) {
    fun fillXLSX() {
        val workbook = XSSFWorkbook()
        createItemSheet(workbook)
        createCategorySheet(workbook)
        createPlaceSheet(workbook)
        createItemCategorySheet(workbook)
        createTransferPathSheet(workbook)
        createTransferSheet(workbook)
        createItemPlaceSheet(workbook)

        val fileOut = FileOutputStream("transfers.xlsx")
        workbook.write(fileOut)
        fileOut.close()
        workbook.close()

    }

    private fun createItemPlaceSheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Предметы на местах")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.CORAL.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.CORAL.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in placeItemFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(placeItemFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getItemPlaces()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.place.id.toString())
                row.createCell(2).setCellValue(item.item.id.toString())
                row.createCell(3).setCellValue(item.quantity.toString())
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
        itemSheet.autoSizeColumn(3)
    }

    private fun createTransferSheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Трансферы")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.BLUE.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.BLUE.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in itemTransferFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(itemTransferFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getItemTransfers()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.transferPath.id.toString())
                row.createCell(2).setCellValue(item.item.id.toString())
                row.createCell(3).setCellValue(item.quantity.toString())
                row.createCell(4).setCellValue(item.datetime.toString())
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
        itemSheet.autoSizeColumn(3)
        itemSheet.autoSizeColumn(4)
    }

    private fun createTransferPathSheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Пути трансферов")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.RED.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.RED.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in transferPathFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(transferPathFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getTransferPaths()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.placeFrom.id.toString())
                row.createCell(2).setCellValue(item.placeTo.id.toString())
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
    }

    private fun createItemCategorySheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Категории предметов")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.GREEN.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.GREEN.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in itemCategoryFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(itemCategoryFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getItemCategories()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.item.id.toString())
                row.createCell(2).setCellValue(item.category.id.toString())
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
    }

    private fun createPlaceSheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Места")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.BLACK.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.BLACK.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in placeFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(placeFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getPlaces()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.label)
                row.createCell(2).setCellValue(item.description)
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
    }

    private fun createCategorySheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Категории")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.BLUE_GREY.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.BLUE.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in categoryFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(categoryFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getCategories()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.label)
                row.createCell(2).setCellValue(item.description)
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
    }

    private fun createItemSheet(workbook: XSSFWorkbook) {
        val itemSheet = workbook.createSheet("Предметы")
        val headerFont = workbook.createFont()
        headerFont.bold = true
        headerFont.color = IndexedColors.BLUE.getIndex()

        val headerCellStyle = workbook.createCellStyle()
        headerCellStyle.setFont(headerFont)
        headerCellStyle.bottomBorderColor = IndexedColors.BLUE.getIndex()

        // Row for Header
        val headerRow = itemSheet.createRow(0)

        // Header
        for (col in itemFields.indices) {
            val cell = headerRow.createCell(col)
            cell.setCellValue(itemFields[col])
            cell.cellStyle = headerCellStyle
        }

        var rowIdx = 1
        transaction(db) {
            for (item in getItems()) {
                val row = itemSheet.createRow(rowIdx++)
                row.createCell(0).setCellValue(item.id.toString())
                row.createCell(1).setCellValue(item.label)
                row.createCell(2).setCellValue(item.description)
            }
        }
        itemSheet.autoSizeColumn(0)
        itemSheet.autoSizeColumn(1)
        itemSheet.autoSizeColumn(2)
    }

    private val itemFields = arrayOf("Код", "Наименование", "Описание")
    private val categoryFields = arrayOf("Код", "Наименование", "Описание")
    private val itemCategoryFields = arrayOf("Код", "Код предмета", "Код категории")
    private val itemTransferFields = arrayOf("Код", "Код трансфера", "Код предмета", "Количество", "Дата трансфера")
    private val placeFields = arrayOf("Код", "Наименование", "Описание")
    private val placeItemFields = arrayOf("Код", "Код места", "Код предмета", "Количество")
    private val transferPathFields = arrayOf("Код", "Код места убытия", "Код места прибытия")

    private fun getItems() = Item.all()

    private fun getItemTransfers() = Transfer.all()

    private fun getCategories() = Category.all()

    private fun getItemCategories() = ItemCategories.all()

    private fun getPlaces() = Place.all()

    private fun getTransferPaths() = TransferPath.all()

    private fun getItemPlaces() = ItemInPlace.all()
}