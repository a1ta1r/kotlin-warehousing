import org.jetbrains.exposed.sql.Database

object DbConnection {
        val db by lazy {
            Database.connect("jdbc:mysql://localhost:3306/warehousing?sslMode=DISABLED", driver = "com.mysql.jdbc.Driver",
                    user = "root", password = "root")
        }
}