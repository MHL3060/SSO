package local.tux.app.sso

import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class WebSecurityConfiguration(private val userDetailsService: UserDetailsService) : WebSecurityConfigurerAdapter() {
    val passwordEncoder: PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return passwordEncoder
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}