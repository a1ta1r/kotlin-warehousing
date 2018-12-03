import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import java.sql.Connection

object DbConnection {
    val db by lazy {
        Database.connect("jdbc:mysql://localhost:3306/warehousing?sslMode=DISABLED&characterEncoding=utf-8", driver = "com.mysql.jdbc.Driver",
                user = "root", password = "root")
    }

    val sqlite by lazy {
        val db = Database.connect("jdbc:sqlite:D:\\\\sqlite\\warehousing", "org.sqlite.JDBC")
        TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE
        db
    }
}