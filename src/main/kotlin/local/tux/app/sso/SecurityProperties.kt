package local.tux.app.sso

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.core.io.Resource

@ConfigurationProperties("security")
class SecurityProperties {
    var jwt: JwtProperty = JwtProperty()
}

class JwtProperty {
    lateinit var keyStore: Resource
    lateinit var keyStorePassword: String
    lateinit var keyPairAlias: String
    lateinit var keyPairPassword: String
}