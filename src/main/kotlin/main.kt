import io.ktor.application.call
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import warehousing.entities.Category
import warehousing.entities.Item
import warehousing.entities.ItemCategories
import warehousing.tables.*

fun main(args: Array<String>) {
    DbConnection.db
    transaction {
        SchemaUtils.create(
                PlaceTypeTable,
                PlaceTable,
                ItemTable,
                CategoryTable,
                ItemCategoryTable,
                PlaceItemTable,
                TransferPathTable,
                TransferTable,
                SaleTable
        )
        Category.new {
            label = "Toys"
            description = "good toys"
        }
        Category.new {
            label = "Food"
            description = "good food"
        }
        Category.new {
            label = "Misc"
            description = "good misc."
        }
        Item.new {
            label = "soldier"
            description = "has a rifle"
        }
        Item.new {
            label = "soldier 2"
            description = "has a pistol"
        }
        Item.new {
            label = "soldier 3"
            description = "has one arm"
        }
        ItemCategories.new {
            item = Item[1]
            category = Category[1]
        }
        ItemCategories.new {
            item = Item[1]
            category = Category[3]
        }
    }
    embeddedServer(Netty, 8080) {
        routing {
            get("/") {
                call.respondText(transaction {
                    addLogger(StdOutSqlLogger)
                    var str = ""
                    Item[1].categories.forEach {str += it.category.label + "\n" }
                    str
                })

            }
        }
    }.start(true)


}