package local.tux.app.sso


import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
import javax.sql.DataSource

@SpringBootApplication
class AppApplication {

    @Bean
    fun userDetailsService(dataSource: DataSource): UserDetailsService {
        val userDetailsService = JdbcDaoImpl()
        userDetailsService.setDataSource(dataSource)
        return userDetailsService
    }
}

fun main(args: Array<String>) {
    runApplication<AppApplication>(*args)
}
