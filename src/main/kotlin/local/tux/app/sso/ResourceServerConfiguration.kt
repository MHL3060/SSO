package local.tux.app.sso

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
@EnableResourceServer
@EnableConfigurationProperties(SecurityProperties::class)
class ResourceServerConfiguration(private val tokenStore: TokenStore): ResourceServerConfigurerAdapter() {


    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.tokenStore(tokenStore)
    }


}