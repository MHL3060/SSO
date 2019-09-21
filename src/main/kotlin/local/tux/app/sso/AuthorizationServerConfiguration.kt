package local.tux.app.sso

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory
import java.security.KeyPair
import org.springframework.security.oauth2.provider.token.TokenStore
import javax.sql.DataSource


@Configuration
@EnableAuthorizationServer
@EnableConfigurationProperties(SecurityProperties::class)
class AuthorizationServerConfiguration(val authenticationManager: AuthenticationManager,
                                       val userDetailsService: UserDetailsService,
                                       val dataSource: DataSource,
                                       val passwordEncoder: PasswordEncoder,
                                       val securityProperties: SecurityProperties): AuthorizationServerConfigurerAdapter() {

    val jwtAccessTokenConverter: JwtAccessTokenConverter
    val tokenStore: TokenStore
    init {
        val jwtProperties = securityProperties.jwt
        val keyPair = keyPair(jwtProperties)
        jwtAccessTokenConverter = JwtAccessTokenConverter()
        jwtAccessTokenConverter.setKeyPair(keyPair)
        tokenStore = JwtTokenStore(jwtAccessTokenConverter)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore())
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.passwordEncoder(this.passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
    }

    @Bean
    fun tokenStore(): TokenStore {
        return tokenStore
    }

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter {
        return jwtAccessTokenConverter
    }


    private fun keyPair(jwtProperties: JwtProperty): KeyPair {
        val keyStoreKeyFactory = KeyStoreKeyFactory(jwtProperties.keyStore, jwtProperties.keyPairPassword.toCharArray())
        return keyStoreKeyFactory.getKeyPair(jwtProperties.keyPairAlias, jwtProperties.keyPairPassword.toCharArray())
    }
}
