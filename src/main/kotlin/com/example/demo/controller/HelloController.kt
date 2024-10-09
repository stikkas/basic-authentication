package com.example.demo.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping
    fun index(auth: Authentication): String {
        println(auth.isAuthenticated)
        println((auth.principal as UserDetails).username)
        println((auth.principal as UserDetails).password)
        return "Hello World!"
    }

    @GetMapping("/out")
    fun close(request: HttpServletRequest, response: HttpServletResponse, auth: Authentication): String {
        SecurityContextLogoutHandler().logout(request, response, auth)
        return "you're out"
    }
}
