import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.stereotype.Service
import javax.sql.DataSource


class LocalDbUserDetailsService(dataSource: DataSource): JdbcDaoImpl() {

    init {
        setDataSource(dataSource)
    }

}