package com.example.demo

import com.example.demo.controller.RequestFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.context.DelegatingSecurityContextRepository
import org.springframework.security.web.context.HttpSessionSecurityContextRepository
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filters(http: HttpSecurity, usDetails: USDetails): SecurityFilterChain {
        return http.csrf {
            it.disable()
        }.cors {
            it.disable()
        }
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
            }
            .authenticationManager {
                var auth = it
                if (!auth.isAuthenticated) {
                    val user = usDetails.loadUserByUsername(it.credentials as String)
                    auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                }
                auth
            }
            .httpBasic(Customizer.withDefaults())
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                it.sessionConcurrency {
                    it.maximumSessions(1)
                    it.maxSessionsPreventsLogin(true)
                }
            }
            .addFilterBefore(RequestFilter(), BasicAuthenticationFilter::class.java)
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence?): String {
                println("Encode: rawPassword=$rawPassword")
                return ""
            }

            override fun matches(rawPassword: CharSequence?, encodedPassword: String?): Boolean {
                println("Match: rawPassword=$rawPassword")
                return true
            }

        }
    }
}
