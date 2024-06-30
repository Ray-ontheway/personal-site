package top.rayc.personalsite.user.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import top.rayc.personalsite.user.security.authentication.JwtGeneratorFilter
import top.rayc.personalsite.user.security.authentication.JwtValidatorFilter
import top.rayc.personalsite.user.security.provider.UsernamePasswordAuthenticationProvider

@Configuration
class CommonConfig {
    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    @Autowired val usernamePasswordAuthenticationProvider: UsernamePasswordAuthenticationProvider,
    @Autowired val jwtValidatorFilter: JwtValidatorFilter,
) {

    @Bean
    fun authenticationManager(): AuthenticationManager {

        return ProviderManager(usernamePasswordAuthenticationProvider)
    }

    @Bean
    fun initialJsonAuthenticationFilter(manager: AuthenticationManager): JwtGeneratorFilter {
        return JwtGeneratorFilter(manager)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity, jwtGeneratorFilter: JwtGeneratorFilter): SecurityFilterChain {

        http.csrf { it.disable() }
            .authorizeHttpRequests { it.anyRequest().permitAll() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(jwtValidatorFilter, BasicAuthenticationFilter::class.java)
            .addFilterBefore(jwtGeneratorFilter, BasicAuthenticationFilter::class.java)

        return http.build()
    }

}
