package com.example.demo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class USDetails : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
//        val sessionUser = SecurityContextHolder.getContext().authentication
//        println(sessionUser?.credentials)
//        println((sessionUser?.principal as? UserDetails)?.username)
//        println((sessionUser?.principal as? UserDetails)?.password)
        println(username)
        return object : UserDetails {
            override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
                return mutableSetOf()
            }

            override fun getPassword(): String {
                return ""
            }

            override fun getUsername(): String {
                return username
            }

            override fun isAccountNonExpired(): Boolean {
                return true
            }

            override fun isAccountNonLocked(): Boolean {
                return true
            }

            override fun isCredentialsNonExpired(): Boolean {
                return true
            }

            override fun isEnabled(): Boolean {
                return true
            }
        }
    }
}
